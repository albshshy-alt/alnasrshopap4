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
        try {
            String currentUrl = webView == null ? null : webView.getUrl();
            if (currentUrl == null || !isSiteUrl(Uri.parse(currentUrl))) {
                throw new IOException("The invoice request did not come from the library site.");
            }
            JSONObject invoice = new JSONObject(invoiceJson);
            File pdfFile = createInvoicePdf(invoice);
            Uri pdfUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".fileprovider",
                    pdfFile);
            shareInvoiceToLibrary(pdfUri, invoice.optString("number", ""));
        } catch (Exception exception) {
            Toast.makeText(this, "تعذر إنشاء الفاتورة PDF. حاول مرة أخرى.", Toast.LENGTH_LONG).show();
        }
    }

    private void shareInvoiceToLibrary(Uri pdfUri, String invoiceNumber) {
        String[] whatsappPackages = {"com.whatsapp", "com.whatsapp.w4b"};
        for (String packageName : whatsappPackages) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setPackage(packageName);
            whatsappIntent.setType("application/pdf");
            whatsappIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            whatsappIntent.putExtra(Intent.EXTRA_SUBJECT, "فاتورة مكتبة النصر " + invoiceNumber);
            whatsappIntent.putExtra("jid", "963954672747@s.whatsapp.net");
            whatsappIntent.setClipData(ClipData.newUri(getContentResolver(), "فاتورة مكتبة النصر", pdfUri));
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
        fallback.putExtra(Intent.EXTRA_SUBJECT, "فاتورة مكتبة النصر " + invoiceNumber);
        fallback.setClipData(ClipData.newUri(getContentResolver(), "فاتورة مكتبة النصر", pdfUri));
        fallback.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent chooser = Intent.createChooser(fallback, "اختر واتساب ثم محادثة مكتبة النصر");
        chooser.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(chooser);
    }

    private File createInvoicePdf(JSONObject invoice) throws IOException {
        JSONArray items = invoice.optJSONArray("items");
        if (items == null || items.length() == 0) {
            throw new IOException("Invoice has no items.");
        }

        String invoiceNumber = invoice.optString("number", "NASR").replaceAll("[^A-Za-z0-9_-]", "");
        if (invoiceNumber.isEmpty()) {
            invoiceNumber = "NASR";
        }
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

                int y = drawInvoiceHeader(canvas, invoice, pageNumber);
                y = drawTableHeader(canvas, y);
                int rowsOnPage = 0;

                while (itemIndex < items.length()) {
                    JSONObject item = items.optJSONObject(itemIndex);
                    if (item == null) {
                        itemIndex++;
                        continue;
                    }
                    String name = item.optString("name", "منتج");
                    int quantity = Math.max(1, item.optInt("qty", 1));
                    double price = Math.max(0, item.optDouble("price", 0));
                    int rowHeight = calculateRowHeight(name);
                    int remainingItems = items.length() - itemIndex - 1;
                    int reservedSpace = 40;
                    if (remainingItems == 0) {
                        reservedSpace = 85;
                        String noteText = invoice.optString("notes", "").trim();
                        if (!noteText.isEmpty()) {
                            int noteTextHeight = makeLayout(noteText, 12, COLOR_INK,
                                    PAGE_WIDTH - PAGE_MARGIN * 2 - 28,
                                    Layout.Alignment.ALIGN_NORMAL, true, 5, true).getHeight();
                            int noteCardHeight = Math.max(56, Math.min(112, noteTextHeight + 34));
                            reservedSpace += 8 + noteCardHeight;
                        }
                    }
                    int contentBottom = PAGE_HEIGHT - 85;
                    if (rowsOnPage > 0 && y + rowHeight + reservedSpace > contentBottom) {
                        break;
                    }

                    drawInvoiceRow(canvas, y, rowHeight, name, quantity, price, rowsOnPage);
                    y += rowHeight;
                    rowsOnPage++;
                    itemIndex++;
                }

                boolean lastPage = itemIndex >= items.length();
                if (lastPage) {
                    y += 15;
                    y = drawInvoiceTotal(canvas, y, invoice.optDouble("total", 0));
                    String notes = invoice.optString("notes", "").trim();
                    if (!notes.isEmpty()) {
                        y = drawInvoiceNotes(canvas, y + 8, notes);
                    }
                } else {
                    drawWrappedText(
                            canvas,
                            "يتبع في الصفحة التالية",
                            PAGE_MARGIN,
                            PAGE_HEIGHT - 78,
                            PAGE_WIDTH - PAGE_MARGIN * 2,
                            11,
                            COLOR_MUTED,
                            Layout.Alignment.ALIGN_CENTER,
                            true,
                            1);
                }

                drawFooter(canvas, pageNumber);
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

    private int drawInvoiceHeader(Canvas canvas, JSONObject invoice, int pageNumber) {
        Paint header = new Paint(Paint.ANTI_ALIAS_FLAG);
        header.setColor(COLOR_GREEN);
        RectF headerRect = new RectF(PAGE_MARGIN, 24, PAGE_WIDTH - PAGE_MARGIN, 130);
        canvas.drawRoundRect(headerRect, 16, 16, header);

        Paint gold = new Paint(Paint.ANTI_ALIAS_FLAG);
        gold.setColor(COLOR_GOLD);
        canvas.drawRoundRect(new RectF(PAGE_WIDTH - PAGE_MARGIN - 7, 38,
                PAGE_WIDTH - PAGE_MARGIN - 3, 116), 2, 2, gold);
        canvas.drawCircle(PAGE_WIDTH - PAGE_MARGIN - 42, 75, 28, gold);
        drawWrappedText(canvas, "ن", PAGE_WIDTH - PAGE_MARGIN - 62, 54, 40, 24,
                COLOR_GREEN, Layout.Alignment.ALIGN_CENTER, true, 1);

        drawWrappedText(canvas, "مكتبة النصر", PAGE_MARGIN + 16, 43, 402,
                27, Color.WHITE, Layout.Alignment.ALIGN_NORMAL, true, 1);
        drawWrappedText(canvas, "دوما · ريف دمشق · سوريا", PAGE_MARGIN + 16, 77, 402,
                13, Color.rgb(244, 230, 190), Layout.Alignment.ALIGN_NORMAL, true, 1);
        canvas.drawRoundRect(new RectF(PAGE_MARGIN + 16, 98, PAGE_MARGIN + 146, 119), 10, 10, gold);
        drawWrappedText(canvas, "فاتورة مبيعات", PAGE_MARGIN + 19, 101, 124,
                10, COLOR_GREEN, Layout.Alignment.ALIGN_CENTER, true, 1);

        int cardY = 145;
        int cardHeight = 80;
        int cardWidth = 259;
        int invoiceCardX = PAGE_WIDTH - PAGE_MARGIN - cardWidth;
        int customerCardX = PAGE_MARGIN;
        Paint cardFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        cardFill.setColor(Color.rgb(248, 245, 237));
        Paint cardBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        cardBorder.setColor(Color.rgb(226, 216, 193));
        cardBorder.setStyle(Paint.Style.STROKE);
        cardBorder.setStrokeWidth(1f);
        RectF invoiceCard = new RectF(invoiceCardX, cardY, invoiceCardX + cardWidth, cardY + cardHeight);
        RectF customerCard = new RectF(customerCardX, cardY, customerCardX + cardWidth, cardY + cardHeight);
        canvas.drawRoundRect(invoiceCard, 11, 11, cardFill);
        canvas.drawRoundRect(invoiceCard, 11, 11, cardBorder);
        canvas.drawRoundRect(customerCard, 11, 11, cardFill);
        canvas.drawRoundRect(customerCard, 11, 11, cardBorder);

        drawWrappedText(canvas, "بيانات الفاتورة", invoiceCardX + 12, cardY + 7, cardWidth - 24,
                11, COLOR_GOLD, Layout.Alignment.ALIGN_NORMAL, true, 1);
        drawWrappedText(canvas, "الرقم: " + invoice.optString("number", "—"),
                invoiceCardX + 12, cardY + 27, cardWidth - 24, 12, COLOR_INK,
                Layout.Alignment.ALIGN_NORMAL, true, 1);
        drawWrappedText(canvas, "التاريخ: " + invoice.optString("dateLabel", "—"),
                invoiceCardX + 12, cardY + 48, cardWidth - 24, 12, COLOR_INK,
                Layout.Alignment.ALIGN_NORMAL, true, 1);

        String customer = invoice.optString("customer", "").trim();
        drawWrappedText(canvas, "بيانات الزبون", customerCardX + 12, cardY + 7, cardWidth - 24,
                11, COLOR_GOLD, Layout.Alignment.ALIGN_NORMAL, true, 1);
        drawWrappedText(canvas, "الاسم: " + (customer.isEmpty() ? "—" : customer),
                customerCardX + 12, cardY + 29, cardWidth - 24, 12, COLOR_INK,
                Layout.Alignment.ALIGN_NORMAL, true, 2);

        drawWrappedText(canvas, "تفاصيل المنتجات", PAGE_MARGIN, 237,
                PAGE_WIDTH - PAGE_MARGIN * 2, 16, COLOR_GREEN,
                Layout.Alignment.ALIGN_NORMAL, true, 1);
        Paint underline = new Paint(Paint.ANTI_ALIAS_FLAG);
        underline.setColor(COLOR_GOLD);
        canvas.drawRoundRect(new RectF(PAGE_MARGIN, 267, PAGE_WIDTH - PAGE_MARGIN, 270), 2, 2, underline);
        return 277;
    }

    private int drawTableHeader(Canvas canvas, int y) {
        int[] widths = {126, 105, 68, 232};
        String[] labels = {"الإجمالي", "السعر", "الكمية", "المنتج"};
        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setColor(COLOR_GREEN);
        canvas.drawRoundRect(new RectF(PAGE_MARGIN, y, PAGE_WIDTH - PAGE_MARGIN, y + 36), 9, 9, fill);
        int x = PAGE_MARGIN;
        for (int i = 0; i < widths.length; i++) {
            drawCellText(canvas, labels[i], x, y, widths[i], 36, 12, Color.WHITE,
                    Layout.Alignment.ALIGN_CENTER, true, 1);
            x += widths[i];
        }
        return y + 40;
    }

    private int calculateRowHeight(String name) {
        StaticLayout layout = makeLayout(name, 12, COLOR_INK, 220,
                Layout.Alignment.ALIGN_NORMAL, true, 2, false);
        return Math.max(40, Math.min(56, layout.getHeight() + 12));
    }

    private void drawInvoiceRow(Canvas canvas, int y, int height, String name, int quantity,
                                double price, int rowIndex) {
        Paint rowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rowPaint.setColor(rowIndex % 2 == 0 ? Color.rgb(250, 248, 242) : Color.WHITE);
        canvas.drawRect(PAGE_MARGIN, y, PAGE_WIDTH - PAGE_MARGIN, y + height, rowPaint);

        Paint accent = new Paint(Paint.ANTI_ALIAS_FLAG);
        accent.setColor(COLOR_GOLD);
        canvas.drawRect(PAGE_WIDTH - PAGE_MARGIN - 3, y + 4, PAGE_WIDTH - PAGE_MARGIN, y + height - 4, accent);

        int[] widths = {126, 105, 68, 232};
        String[] values = {
                formatAmount(quantity * price) + " ل.س",
                formatAmount(price) + " ل.س",
                String.valueOf(quantity),
                name
        };
        int x = PAGE_MARGIN;
        for (int i = 0; i < widths.length; i++) {
            if (i == 2) {
                Paint quantityPill = new Paint(Paint.ANTI_ALIAS_FLAG);
                quantityPill.setColor(Color.rgb(239, 233, 218));
                canvas.drawRoundRect(new RectF(x + 18, y + 8, x + widths[i] - 18, y + height - 8),
                        8, 8, quantityPill);
            }
            drawCellText(canvas, values[i], x, y, widths[i], height, 12, COLOR_INK,
                    i == 3 ? Layout.Alignment.ALIGN_NORMAL : Layout.Alignment.ALIGN_CENTER,
                    i == 3, i == 3 ? 2 : 1);
            x += widths[i];
        }

        Paint line = new Paint(Paint.ANTI_ALIAS_FLAG);
        line.setColor(Color.rgb(229, 224, 212));
        line.setStrokeWidth(0.7f);
        canvas.drawLine(PAGE_MARGIN, y + height, PAGE_WIDTH - PAGE_MARGIN, y + height, line);
    }

    private int drawInvoiceTotal(Canvas canvas, int y, double total) {
        Paint background = new Paint(Paint.ANTI_ALIAS_FLAG);
        background.setColor(COLOR_GREEN);
        canvas.drawRoundRect(new RectF(PAGE_MARGIN, y, PAGE_WIDTH - PAGE_MARGIN, y + 62), 11, 11, background);

        Paint amountBadge = new Paint(Paint.ANTI_ALIAS_FLAG);
        amountBadge.setColor(COLOR_GOLD);
        canvas.drawRoundRect(new RectF(PAGE_MARGIN + 10, y + 10, PAGE_MARGIN + 205, y + 52),
                9, 9, amountBadge);
        drawWrappedText(canvas, formatAmount(total) + " ل.س", PAGE_MARGIN + 16, y + 20, 183,
                16, COLOR_GREEN, Layout.Alignment.ALIGN_CENTER, false, 1);
        drawWrappedText(canvas, "الإجمالي النهائي", PAGE_MARGIN + 218, y + 12,
                PAGE_WIDTH - PAGE_MARGIN * 2 - 232, 16, Color.WHITE,
                Layout.Alignment.ALIGN_NORMAL, true, 1);
        drawWrappedText(canvas, "المبلغ المطلوب دفعه", PAGE_MARGIN + 218, y + 35,
                PAGE_WIDTH - PAGE_MARGIN * 2 - 232, 10, Color.rgb(231, 224, 205),
                Layout.Alignment.ALIGN_NORMAL, true, 1);
        return y + 70;
    }

    private int drawInvoiceNotes(Canvas canvas, int y, String notes) {
        int x = PAGE_MARGIN;
        int width = PAGE_WIDTH - PAGE_MARGIN * 2;
        StaticLayout noteLayout = makeLayout(notes, 12, COLOR_INK, width - 28,
                Layout.Alignment.ALIGN_NORMAL, true, 5, true);
        int height = Math.max(56, Math.min(112, noteLayout.getHeight() + 34));
        RectF card = new RectF(x, y, x + width, y + height);
        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setColor(Color.rgb(250, 248, 242));
        Paint border = new Paint(Paint.ANTI_ALIAS_FLAG);
        border.setColor(Color.rgb(226, 216, 193));
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(1f);
        canvas.drawRoundRect(card, 9, 9, fill);
        canvas.drawRoundRect(card, 9, 9, border);
        drawWrappedText(canvas, "ملاحظات الطلب", x + 12, y + 6, width - 24,
                10, COLOR_GOLD, Layout.Alignment.ALIGN_NORMAL, true, 1);
        drawWrappedText(canvas, notes, x + 12, y + 23, width - 24,
                12, COLOR_INK, Layout.Alignment.ALIGN_NORMAL, true, 5);
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
        int typeStyle = size >= 16 ? Typeface.BOLD : Typeface.NORMAL;
        textPaint.setTypeface(Typeface.create("sans-serif", typeStyle));

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

    private void drawFooter(Canvas canvas, int pageNumber) {
        Paint line = new Paint(Paint.ANTI_ALIAS_FLAG);
        line.setColor(COLOR_GOLD);
        line.setStrokeWidth(1.2f);
        canvas.drawLine(PAGE_MARGIN, PAGE_HEIGHT - 68, PAGE_WIDTH - PAGE_MARGIN, PAGE_HEIGHT - 68, line);
        drawWrappedText(canvas, "جميع الحقوق محفوظة لدى مكتبة النصر دوما",
                PAGE_MARGIN, PAGE_HEIGHT - 61, PAGE_WIDTH - PAGE_MARGIN * 2,
                10, COLOR_GREEN, Layout.Alignment.ALIGN_CENTER, true, 1);
        drawWrappedText(canvas, "ريف دمشق · 0954 672 747",
                PAGE_MARGIN, PAGE_HEIGHT - 43, PAGE_WIDTH - PAGE_MARGIN * 2,
                9, COLOR_MUTED, Layout.Alignment.ALIGN_CENTER, true, 1);
        drawWrappedText(canvas, "صفحة " + pageNumber,
                PAGE_MARGIN, PAGE_HEIGHT - 25, PAGE_WIDTH - PAGE_MARGIN * 2,
                8, COLOR_MUTED, Layout.Alignment.ALIGN_CENTER, false, 1);
    }

    private String formatAmount(double value) {
        return String.format(Locale.US, "%,.0f", value);
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
