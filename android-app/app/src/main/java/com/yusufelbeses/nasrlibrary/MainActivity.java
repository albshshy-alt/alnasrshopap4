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

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "فاتورة مكتبة النصر " + invoice.optString("number", ""));
            shareIntent.putExtra(Intent.EXTRA_TEXT, "فاتورة مكتبة النصر — دوما");
            shareIntent.setClipData(ClipData.newUri(getContentResolver(), "فاتورة مكتبة النصر", pdfUri));
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Intent chooser = Intent.createChooser(shareIntent, "مشاركة الفاتورة كملف PDF");
            chooser.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(chooser);
        } catch (Exception exception) {
            Toast.makeText(this, "تعذر إنشاء الفاتورة PDF. حاول مرة أخرى.", Toast.LENGTH_LONG).show();
        }
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
                    int reservedSpace = remainingItems == 0
                            ? (invoice.optString("notes", "").trim().isEmpty() ? 110 : 155)
                            : 40;
                    int contentBottom = PAGE_HEIGHT - 58;
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
                        y += 8;
                        int notesHeight = drawWrappedText(
                                canvas,
                                "ملاحظات: " + notes,
                                PAGE_MARGIN,
                                y,
                                PAGE_WIDTH - PAGE_MARGIN * 2,
                                13,
                                COLOR_MUTED,
                                Layout.Alignment.ALIGN_NORMAL,
                                true,
                                5);
                        y += notesHeight;
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
        Paint background = new Paint(Paint.ANTI_ALIAS_FLAG);
        background.setColor(COLOR_GREEN);
        canvas.drawRect(0, 0, PAGE_WIDTH, 91, background);

        drawWrappedText(canvas, "مكتبة النصر", PAGE_MARGIN, 15, PAGE_WIDTH - PAGE_MARGIN * 2,
                26, Color.WHITE, Layout.Alignment.ALIGN_CENTER, true, 1);
        drawWrappedText(canvas, "فاتورة مبيعات · دوما", PAGE_MARGIN, 51, PAGE_WIDTH - PAGE_MARGIN * 2,
                13, Color.rgb(244, 230, 190), Layout.Alignment.ALIGN_CENTER, true, 1);

        int fullWidth = PAGE_WIDTH - PAGE_MARGIN * 2;
        int y = 105;
        drawWrappedText(canvas, "رقم الفاتورة: " + invoice.optString("number", "—"),
                PAGE_MARGIN, y, fullWidth, 13, COLOR_INK, Layout.Alignment.ALIGN_NORMAL, true, 1);
        y += 23;
        drawWrappedText(canvas, "التاريخ: " + invoice.optString("dateLabel", "—"),
                PAGE_MARGIN, y, fullWidth, 13, COLOR_INK, Layout.Alignment.ALIGN_NORMAL, true, 1);
        y += 23;
        String customer = invoice.optString("customer", "").trim();
        drawWrappedText(canvas, "الزبون: " + (customer.isEmpty() ? "—" : customer),
                PAGE_MARGIN, y, fullWidth, 13, COLOR_INK, Layout.Alignment.ALIGN_NORMAL, true, 2);
        return 185;
    }

    private int drawTableHeader(Canvas canvas, int y) {
        int[] widths = {126, 105, 68, 232};
        String[] labels = {"الإجمالي", "السعر", "الكمية", "المنتج"};
        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setColor(Color.rgb(239, 233, 218));
        canvas.drawRoundRect(new RectF(PAGE_MARGIN, y, PAGE_WIDTH - PAGE_MARGIN, y + 34), 6, 6, fill);

        int x = PAGE_MARGIN;
        for (int i = 0; i < widths.length; i++) {
            drawCellText(canvas, labels[i], x, y, widths[i], 34, 12, COLOR_GREEN,
                    Layout.Alignment.ALIGN_CENTER, true, 1);
            x += widths[i];
        }
        return y + 36;
    }

    private int calculateRowHeight(String name) {
        StaticLayout layout = makeLayout(name, 12, COLOR_INK, 220,
                Layout.Alignment.ALIGN_NORMAL, true, 2, false);
        return Math.max(38, Math.min(56, layout.getHeight() + 10));
    }

    private void drawInvoiceRow(Canvas canvas, int y, int height, String name, int quantity,
                                double price, int rowIndex) {
        Paint rowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rowPaint.setColor(rowIndex % 2 == 0 ? Color.rgb(250, 248, 242) : Color.WHITE);
        canvas.drawRect(PAGE_MARGIN, y, PAGE_WIDTH - PAGE_MARGIN, y + height, rowPaint);

        int[] widths = {126, 105, 68, 232};
        String[] values = {
                formatAmount(quantity * price) + " ل.س",
                formatAmount(price) + " ل.س",
                String.valueOf(quantity),
                name
        };
        int x = PAGE_MARGIN;
        for (int i = 0; i < widths.length; i++) {
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
        canvas.drawRoundRect(new RectF(PAGE_MARGIN, y, PAGE_WIDTH - PAGE_MARGIN, y + 45), 7, 7, background);
        drawWrappedText(canvas, "المجموع الكلي: " + formatAmount(total) + " ل.س",
                PAGE_MARGIN + 10, y + 11, PAGE_WIDTH - PAGE_MARGIN * 2 - 20,
                17, Color.WHITE, Layout.Alignment.ALIGN_NORMAL, true, 1);
        return y + 54;
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

    private void drawFooter(Canvas canvas, int pageNumber) {
        Paint line = new Paint(Paint.ANTI_ALIAS_FLAG);
        line.setColor(COLOR_GOLD);
        line.setStrokeWidth(1f);
        canvas.drawLine(PAGE_MARGIN, PAGE_HEIGHT - 48, PAGE_WIDTH - PAGE_MARGIN, PAGE_HEIGHT - 48, line);
        drawWrappedText(canvas, "مكتبة النصر · دوما · 0954 672 747",
                PAGE_MARGIN, PAGE_HEIGHT - 39, PAGE_WIDTH - PAGE_MARGIN * 2,
                10, COLOR_MUTED, Layout.Alignment.ALIGN_CENTER, true, 1);
        drawWrappedText(canvas, "صفحة " + pageNumber,
                PAGE_MARGIN, PAGE_HEIGHT - 21, PAGE_WIDTH - PAGE_MARGIN * 2,
                9, COLOR_MUTED, Layout.Alignment.ALIGN_CENTER, false, 1);
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
