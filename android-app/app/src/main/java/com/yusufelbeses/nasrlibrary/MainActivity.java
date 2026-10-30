package com.yusufelbeses.nasrlibrary;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public final class MainActivity extends Activity {
    private static final String SITE_URL = "https://albshshy-alt.github.io/alnasrshopap4/";
    private static final String SITE_HOST = "albshshy-alt.github.io";
    private static final int PAGE_WIDTH = 595;
    private static final int PAGE_HEIGHT = 842;
    private static final int PAGE_MARGIN = 32;
    private static final int COLOR_GREEN = Color.rgb(31, 58, 46);
    private static final int COLOR_POS_TEAL = Color.rgb(41, 68, 77);
    private static final int COLOR_GOLD = Color.rgb(201, 162, 74);
    private static final int COLOR_INK = Color.rgb(43, 43, 38);
    private static final int COLOR_MUTED = Color.rgb(105, 99, 86);

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(COLOR_GREEN);
        getWindow().setNavigationBarColor(COLOR_GREEN);

        webView = new WebView(this);
        webView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if (isSiteUrl(uri)) {
                    return false;
                }
                openExternal(uri);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (isSiteUrl(uri)) {
                    return false;
                }
                openExternal(uri);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (request.isForMainFrame()) {
                    showOfflineMessage();
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView popup = new WebView(MainActivity.this);
                WebSettings popupSettings = popup.getSettings();
                popupSettings.setJavaScriptEnabled(true);
                popupSettings.setDomStorageEnabled(true);
                popup.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView child, WebResourceRequest request) {
                        return handlePopupUrl(child, request.getUrl());
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView child, String url) {
                        return handlePopupUrl(child, Uri.parse(url));
                    }

                    @Override
                    public void onPageStarted(WebView child, String url, Bitmap favicon) {
                        Uri uri = Uri.parse(url);
                        if (!isSiteUrl(uri)) {
                            openExternal(uri);
                            child.stopLoading();
                            child.destroy();
                        }
                    }
                });

                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(popup);
                resultMsg.sendToTarget();
                return true;
            }
        });

        webView.addJavascriptInterface(new InvoiceBridge(), "NasrInvoice");
        setContentView(webView);
        webView.loadUrl(SITE_URL);
    }

    private final class InvoiceBridge {
        @JavascriptInterface
        public void shareInvoicePdf(String invoiceJson) {
            runOnUiThread(() -> createAndShareInvoice(invoiceJson));
        }
    }

    private void createAndShareInvoice(String invoiceJson) {
        String language = "ar";
        boolean english = false;
        boolean turkish = false;
        try {
            String currentUrl = webView == null ? null : webView.getUrl();
            if (currentUrl == null || !isSiteUrl(Uri.parse(currentUrl))) {
                throw new IOException("The invoice request did not come from the library site.");
            }
            JSONObject invoice = new JSONObject(invoiceJson);
            language = invoice.optString("language", "ar");
            english = "en".equalsIgnoreCase(language);
            turkish = "tr".equalsIgnoreCase(language);
            File pdfFile = createInvoicePdf(invoice);
            Uri pdfUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".fileprovider",
                    pdfFile);
            shareInvoiceToLibrary(pdfUri, invoice.optString("number", ""), english, turkish);
        } catch (Exception exception) {
            String message = english ? "Could not create the PDF invoice. Please try again."
                    : turkish ? "PDF fatura oluşturulamadı. Lütfen tekrar deneyin."
                    : "تعذر إنشاء الفاتورة PDF. حاول مرة أخرى.";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    private void shareInvoiceToLibrary(Uri pdfUri, String invoiceNumber, boolean english, boolean turkish) {
        String subject = english ? "Nasr Library invoice " + invoiceNumber
                : turkish ? "Nasr Kitabevi faturası " + invoiceNumber
                : "فاتورة مكتبة النصر " + invoiceNumber;
        String chooserTitle = english ? "Choose WhatsApp, then the Nasr Library chat"
                : turkish ? "WhatsApp'ı ve Nasr Kitabevi sohbetini seçin"
                : "اختر واتساب ثم محادثة مكتبة النصر";
        String clipLabel = english ? "Nasr Library invoice" : turkish ? "Nasr Kitabevi faturası" : "فاتورة مكتبة النصر";
        String[] whatsappPackages = {"com.whatsapp", "com.whatsapp.w4b"};
        for (String packageName : whatsappPackages) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setPackage(packageName);
            whatsappIntent.setType("application/pdf");
            whatsappIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            whatsappIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            whatsappIntent.putExtra("jid", "963954672747@s.whatsapp.net");
            whatsappIntent.setClipData(ClipData.newUri(getContentResolver(), clipLabel, pdfUri));
            whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(whatsappIntent);
                return;
            } catch (ActivityNotFoundException | SecurityException ignored) {
                // Try WhatsApp Business, then fall back to the Android share sheet.
            }
        }

        Intent fallback = new Intent(Intent.ACTION_SEND);
        fallback.setType("application/pdf");
        fallback.putExtra(Intent.EXTRA_STREAM, pdfUri);
        fallback.putExtra(Intent.EXTRA_SUBJECT, subject);
        fallback.setClipData(ClipData.newUri(getContentResolver(), clipLabel, pdfUri));
        fallback.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent chooser = Intent.createChooser(fallback, chooserTitle);
        chooser.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(chooser);
    }

    private File createInvoicePdf(JSONObject invoice) throws IOException {
        JSONArray items = invoice.optJSONArray("items");
        if (items == null || items.length() == 0) {
            throw new IOException("Invoice has no items.");
        }

        String invoiceNumber = invoice.optString("number", "NASR").replaceAll("[^A-Za-z0-9_-]", "");
        if (invoiceNumber.isEmpty()) invoiceNumber = "NASR";
        File invoiceDirectory = new File(getCacheDir(), "invoices");
        if (!invoiceDirectory.exists() && !invoiceDirectory.mkdirs()) {
            throw new IOException("Could not create invoice cache directory.");
        }
        File pdfFile = new File(invoiceDirectory, "invoice-" + invoiceNumber + ".pdf");

        PdfDocument document = new PdfDocument();
        try {
            int itemIndex = 0;
            int pageNumber = 1;
            do {
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(
                        PAGE_WIDTH, PAGE_HEIGHT, pageNumber).create();
                PdfDocument.Page page = document.startPage(pageInfo);
                Canvas canvas = page.getCanvas();
                canvas.drawColor(Color.WHITE);

                String language = invoice.optString("language", "ar");
                boolean english = "en".equalsIgnoreCase(language);
                boolean turkish = "tr".equalsIgnoreCase(language);
                boolean rtl = !english && !turkish;
                int y = drawInvoiceHeader(canvas, invoice, pageNumber, english, turkish);
                y = drawTableHeader(canvas, y, english, turkish);
                int rowsOnPage = 0;

                while (itemIndex < items.length()) {
                    JSONObject item = items.optJSONObject(itemIndex);
                    if (item == null) {
                        itemIndex++;
                        continue;
                    }
                    String fallbackName = english ? "Product" : turkish ? "Ürün" : "منتج";
                    String nameKey = english ? "nameEn" : turkish ? "nameTr" : "nameAr";
                    String name = item.optString(nameKey, item.optString("name", fallbackName));
                    int quantity = Math.max(1, item.optInt("qty", 1));
                    double price = Math.max(0, item.optDouble("price", 0));
                    int rowHeight = calculateRowHeight(name, rtl);
                    int remainingItems = items.length() - itemIndex - 1;
                    int reservedSpace = remainingItems == 0
                            ? (invoice.optString("notes", "").trim().isEmpty() ? 112 : 165)
                            : 40;
                    int contentBottom = PAGE_HEIGHT - 58;
                    if (rowsOnPage > 0 && y + rowHeight + reservedSpace > contentBottom) break;

                    drawInvoiceRow(canvas, y, rowHeight, name, quantity, price, rowsOnPage, english, turkish);
                    y += rowHeight;
                    rowsOnPage++;
                    itemIndex++;
                }

                boolean lastPage = itemIndex >= items.length();
                if (lastPage) {
                    y += 14;
                    y = drawInvoiceTotal(canvas, y, invoice.optDouble("total", 0), english, turkish);
                    String notes = invoice.optString("notes", "").trim();
                    if (!notes.isEmpty()) y = drawInvoiceNotes(canvas, y + 8, notes, english, turkish);
                } else {
                    String continued = english ? "Continued on next page" : turkish ? "Sonraki sayfada devam ediyor" : "يتبع في الصفحة التالية";
                    drawWrappedText(canvas, continued,
                            PAGE_MARGIN, PAGE_HEIGHT - 78, PAGE_WIDTH - PAGE_MARGIN * 2,
                            11, COLOR_MUTED, Layout.Alignment.ALIGN_CENTER, rtl, 1);
                }

                drawFooter(canvas, pageNumber, english, turkish);
                document.finishPage(page);
                pageNumber++;
            } while (itemIndex < items.length());

            try (FileOutputStream output = new FileOutputStream(pdfFile)) {
                document.writeTo(output);
                output.flush();
            }
        } finally {
            document.close();
        }
        return pdfFile;
    }

    private int drawInvoiceHeader(Canvas canvas, JSONObject invoice, int pageNumber, boolean english, boolean turkish) {
        boolean rtl = !english && !turkish;
        Paint accent = new Paint(Paint.ANTI_ALIAS_FLAG);
        accent.setColor(COLOR_POS_TEAL);
        canvas.drawRect(0, 0, PAGE_WIDTH, 7, accent);

        String brand = english ? "Nasr Library" : turkish ? "Nasr Kitabevi" : "مكتبة النصر";
        String title = english ? "Sales Invoice · Douma" : turkish ? "Satış Faturası · Duma" : "فاتورة مبيعات · دوما";
        drawWrappedText(canvas, brand, PAGE_MARGIN, 18, PAGE_WIDTH - PAGE_MARGIN * 2,
                23, COLOR_POS_TEAL, Layout.Alignment.ALIGN_CENTER, rtl, 1);
        drawWrappedText(canvas, title, PAGE_MARGIN, 48, PAGE_WIDTH - PAGE_MARGIN * 2,
                12, COLOR_MUTED, Layout.Alignment.ALIGN_CENTER, rtl, 1);

        Paint divider = new Paint(Paint.ANTI_ALIAS_FLAG);
        divider.setColor(Color.rgb(203, 212, 216));
        divider.setStrokeWidth(1f);
        canvas.drawLine(PAGE_MARGIN, 76, PAGE_WIDTH - PAGE_MARGIN, 76, divider);

        Paint card = new Paint(Paint.ANTI_ALIAS_FLAG);
        card.setColor(Color.rgb(245, 247, 248));
        canvas.drawRoundRect(new RectF(PAGE_MARGIN, 91, PAGE_WIDTH - PAGE_MARGIN, 190), 6, 6, card);
        String invoiceLabel = english ? "Invoice no.: " : turkish ? "Fatura no.: " : "رقم الفاتورة: ";
        String dateLabel = english ? "Date: " : turkish ? "Tarih: " : "التاريخ: ";
        String customerLabel = english ? "Customer: " : turkish ? "Müşteri: " : "الزبون: ";
        String customer = invoice.optString("customer", "").trim();
        int fullWidth = PAGE_WIDTH - PAGE_MARGIN * 2 - 22;
        int textX = PAGE_MARGIN + 11;
        drawWrappedText(canvas, invoiceLabel + invoice.optString("number", "—"),
                textX, 101, fullWidth, 12, COLOR_INK, Layout.Alignment.ALIGN_NORMAL, rtl, 1);
        drawWrappedText(canvas, dateLabel + invoice.optString("dateLabel", "—"),
                textX, 124, fullWidth, 12, COLOR_INK, Layout.Alignment.ALIGN_NORMAL, rtl, 1);
        drawWrappedText(canvas, customerLabel + (customer.isEmpty() ? "—" : customer),
                textX, 147, fullWidth, 12, COLOR_INK, Layout.Alignment.ALIGN_NORMAL, rtl, 2);
        return 201;
    }

    private int drawTableHeader(Canvas canvas, int y, boolean english, boolean turkish) {
        int[] widths = {126, 105, 68, 232};
        String[] labels = english
                ? new String[]{"Amount", "Price", "Qty", "Product"}
                : turkish ? new String[]{"Tutar", "Fiyat", "Adet", "Ürün"}
                : new String[]{"الإجمالي", "السعر", "الكمية", "المنتج"};
        boolean rtl = !english && !turkish;
        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setColor(COLOR_POS_TEAL);
        canvas.drawRoundRect(new RectF(PAGE_MARGIN, y, PAGE_WIDTH - PAGE_MARGIN, y + 35), 4, 4, fill);
        int x = PAGE_MARGIN;
        for (int i = 0; i < widths.length; i++) {
            drawCellText(canvas, labels[i], x, y, widths[i], 35, 11.5f, Color.WHITE,
                    Layout.Alignment.ALIGN_CENTER, rtl, 1);
            x += widths[i];
        }
        return y + 37;
    }

    private int calculateRowHeight(String name, boolean rtl) {
        StaticLayout layout = makeLayout(name, 12, COLOR_INK, 220,
                Layout.Alignment.ALIGN_NORMAL, rtl, 2, false);
        return Math.max(38, Math.min(56, layout.getHeight() + 12));
    }

    private void drawInvoiceRow(Canvas canvas, int y, int height, String name, int quantity,
                                double price, int rowIndex, boolean english, boolean turkish) {
        boolean rtl = !english && !turkish;
        Paint rowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rowPaint.setColor(rowIndex % 2 == 0 ? Color.WHITE : Color.rgb(240, 243, 244));
        canvas.drawRect(PAGE_MARGIN, y, PAGE_WIDTH - PAGE_MARGIN, y + height, rowPaint);

        int[] widths = {126, 105, 68, 232};
        String currency = english || turkish ? " SYP" : " ل.س";
        String[] values = {
                formatAmount(quantity * price) + currency,
                formatAmount(price) + currency,
                String.valueOf(quantity),
                name
        };
        Paint grid = new Paint(Paint.ANTI_ALIAS_FLAG);
        grid.setColor(Color.rgb(213, 221, 224));
        grid.setStrokeWidth(0.7f);
        int x = PAGE_MARGIN;
        for (int i = 0; i < widths.length; i++) {
            drawCellText(canvas, values[i], x, y, widths[i], height, 11.5f, COLOR_INK,
                    i == 3 ? Layout.Alignment.ALIGN_NORMAL : Layout.Alignment.ALIGN_CENTER,
                    i == 3 && rtl, i == 3 ? 2 : 1);
            x += widths[i];
            if (i < widths.length - 1) canvas.drawLine(x, y, x, y + height, grid);
        }
        canvas.drawLine(PAGE_MARGIN, y + height, PAGE_WIDTH - PAGE_MARGIN, y + height, grid);
    }

    private int drawInvoiceTotal(Canvas canvas, int y, double total, boolean english, boolean turkish) {
        boolean rtl = !english && !turkish;
        int height = 48;
        Paint background = new Paint(Paint.ANTI_ALIAS_FLAG);
        background.setColor(Color.rgb(238, 242, 243));
        canvas.drawRoundRect(new RectF(PAGE_MARGIN, y, PAGE_WIDTH - PAGE_MARGIN, y + height), 5, 5, background);
        Paint marker = new Paint(Paint.ANTI_ALIAS_FLAG);
        marker.setColor(COLOR_POS_TEAL);
        float markerLeft = rtl ? PAGE_WIDTH - PAGE_MARGIN - 5 : PAGE_MARGIN;
        canvas.drawRect(markerLeft, y + 3, markerLeft + 5, y + height - 3, marker);
        String label = english ? "Grand total" : turkish ? "Genel toplam" : "المجموع الكلي";
        String currency = english || turkish ? " SYP" : " ل.س";
        drawWrappedText(canvas, label + ": " + formatAmount(total) + currency,
                PAGE_MARGIN + 13, y + 13, PAGE_WIDTH - PAGE_MARGIN * 2 - 26,
                15, COLOR_POS_TEAL, Layout.Alignment.ALIGN_NORMAL, rtl, 1);
        return y + height + 8;
    }

    private int drawInvoiceNotes(Canvas canvas, int y, String notes, boolean english, boolean turkish) {
        boolean rtl = !english && !turkish;
        String label = english ? "Notes: " : turkish ? "Notlar: " : "ملاحظات: ";
        int width = PAGE_WIDTH - PAGE_MARGIN * 2 - 20;
        StaticLayout layout = makeLayout(label + notes, 12, COLOR_INK, width,
                Layout.Alignment.ALIGN_NORMAL, rtl, 5, true);
        int height = layout.getHeight() + 18;
        Paint background = new Paint(Paint.ANTI_ALIAS_FLAG);
        background.setColor(Color.rgb(248, 250, 251));
        canvas.drawRoundRect(new RectF(PAGE_MARGIN, y, PAGE_WIDTH - PAGE_MARGIN, y + height), 4, 4, background);
        Paint border = new Paint(Paint.ANTI_ALIAS_FLAG);
        border.setColor(Color.rgb(213, 221, 224));
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(1f);
        canvas.drawRoundRect(new RectF(PAGE_MARGIN, y, PAGE_WIDTH - PAGE_MARGIN, y + height), 4, 4, border);
        canvas.save();
        canvas.translate(PAGE_MARGIN + 10, y + 9);
        layout.draw(canvas);
        canvas.restore();
        return y + height;
    }



    private void drawCellText(Canvas canvas, String text, int x, int y, int width, int height,
                              float size, int color, Layout.Alignment alignment, boolean rtl,
                              int maxLines) {
        StaticLayout layout = makeLayout(text, size, color, Math.max(12, width - 10),
                alignment, rtl, maxLines, maxLines > 1);
        float textY = y + Math.max(3, (height - layout.getHeight()) / 2f);
        canvas.save();
        canvas.clipRect(x + 4, y + 2, x + width - 4, y + height - 2);
        canvas.translate(x + 5, textY);
        layout.draw(canvas);
        canvas.restore();
    }

    private int drawWrappedText(Canvas canvas, String text, int x, int y, int width, float size,
                                int color, Layout.Alignment alignment, boolean rtl, int maxLines) {
        StaticLayout layout = makeLayout(text, size, color, width, alignment, rtl, maxLines, maxLines > 1);
        canvas.save();
        canvas.translate(x, y);
        layout.draw(canvas);
        canvas.restore();
        return layout.getHeight();
    }

    private StaticLayout makeLayout(String text, float size, int color, int width,
                                    Layout.Alignment alignment, boolean rtl, int maxLines,
                                    boolean ellipsize) {
        String value = text == null || text.isEmpty() ? " " : text;
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        textPaint.setColor(color);
        textPaint.setTextSize(size);
        textPaint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));

        StaticLayout.Builder builder = StaticLayout.Builder.obtain(value, 0, value.length(), textPaint, Math.max(12, width))
                .setAlignment(alignment)
                .setTextDirection(rtl ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR)
                .setIncludePad(false)
                .setLineSpacing(0, 1.08f);
        if (maxLines > 0) {
            builder.setMaxLines(maxLines);
        }
        if (ellipsize) {
            builder.setEllipsize(TextUtils.TruncateAt.END);
        }
        return builder.build();
    }

    private void drawFooter(Canvas canvas, int pageNumber, boolean english, boolean turkish) {
        boolean rtl = !english && !turkish;
        Paint line = new Paint(Paint.ANTI_ALIAS_FLAG);
        line.setColor(COLOR_GOLD);
        line.setStrokeWidth(1f);
        canvas.drawLine(PAGE_MARGIN, PAGE_HEIGHT - 50, PAGE_WIDTH - PAGE_MARGIN, PAGE_HEIGHT - 50, line);
        String rights = english ? "All rights reserved to Nasr Library, Douma"
                : turkish ? "Tüm hakları Duma'daki Nasr Kitabevi'ne aittir"
                : "جميع الحقوق محفوظة لدى مكتبة النصر دوما";
        String contact = english ? "Nasr Library · Douma · 0954 672 747"
                : turkish ? "Nasr Kitabevi · Duma · 0954 672 747"
                : "مكتبة النصر · دوما · 0954 672 747";
        String page = (english ? "Page " : turkish ? "Sayfa " : "صفحة ") + pageNumber;
        drawWrappedText(canvas, rights, PAGE_MARGIN, PAGE_HEIGHT - 44,
                PAGE_WIDTH - PAGE_MARGIN * 2, 9, COLOR_POS_TEAL, Layout.Alignment.ALIGN_CENTER, rtl, 1);
        drawWrappedText(canvas, contact, PAGE_MARGIN, PAGE_HEIGHT - 28,
                PAGE_WIDTH - PAGE_MARGIN * 2, 9, COLOR_MUTED, Layout.Alignment.ALIGN_CENTER, rtl, 1);
        drawWrappedText(canvas, page, PAGE_MARGIN, PAGE_HEIGHT - 13,
                PAGE_WIDTH - PAGE_MARGIN * 2, 8, COLOR_MUTED, Layout.Alignment.ALIGN_CENTER, rtl, 1);
    }

    private String formatAmount(double value) {
        return String.format(Locale.US, "%.0f", value);
    }

    private boolean handlePopupUrl(WebView popup, Uri uri) {
        if (!isSiteUrl(uri)) {
            openExternal(uri);
            popup.stopLoading();
            popup.destroy();
            return true;
        }
        return false;
    }

    private boolean isSiteUrl(Uri uri) {
        return "https".equalsIgnoreCase(uri.getScheme())
                && SITE_HOST.equalsIgnoreCase(uri.getHost());
    }

    private void openExternal(Uri uri) {
        if (uri.getScheme() == null) {
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Toast.makeText(this, "لا يوجد تطبيق لفتح هذا الرابط", Toast.LENGTH_LONG).show();
        }
    }

    private void showOfflineMessage() {
        String page = "<!doctype html><html lang='ar' dir='rtl'><meta charset='utf-8'>"
                + "<meta name='viewport' content='width=device-width,initial-scale=1'>"
                + "<body style='font-family:sans-serif;background:#f7f1e3;color:#1f3a2e;"
                + "display:grid;place-items:center;height:90vh;text-align:center;padding:24px'>"
                + "<div><h2>تعذر تحميل مكتبة النصر</h2><p>تحقق من اتصال الإنترنت ثم أعد المحاولة.</p>"
                + "<button onclick=\"location.href='" + SITE_URL + "'\" style='padding:12px 20px;"
                + "background:#1f3a2e;color:white;border:0;border-radius:8px'>إعادة المحاولة</button>"
                + "</div></body></html>";
        webView.loadDataWithBaseURL(null, page, "text/html", "UTF-8", null);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.removeJavascriptInterface("NasrInvoice");
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
