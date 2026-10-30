(() => {
  const LANGUAGE_KEY = "nasrLibraryLanguage";
  const HISTORY_KEY = "nasrLibraryOperationsV1";
  const ENGLISH = {
    "مكتبة النصر": "Nasr Library",
    "دوما": "Douma",
    "دوما · ريف دمشق": "Douma · Rural Damascus",
    "مكتبة النصر · دوما": "Nasr Library · Douma",
    "مكتبة النصر | دوما": "Nasr Library | Douma",
    "ن": "N",
    "ق": "S",
    "م": "M",
    "ه": "G",
    "ع": "P",
    "ل": "T",
    "ط": "P",
    "الإعدادات": "Settings",
    "إغلاق الإعدادات": "Close settings",
    "العمليات السابقة": "Previous transactions",
    "عرض فواتير البيع المحفوظة على هذا الجهاز": "View sales invoices saved on this device",
    "سجل العمليات": "Transaction history",
    "مسح السجل": "Clear history",
    "السجل محفوظ محلياً على هذا الجهاز فقط.": "History is stored locally on this device only.",
    "اللغة / Language": "Language",
    "اختيار اللغة": "Choose language",
    "العربية": "Arabic",
    "سياسة الخصوصية والملكية": "Privacy & ownership",
    "يمنع نسخ أو تقليد التطبيق دون إذن من المالك.": "Copying or imitating this app without the owner's permission is prohibited.",
    "خدمة العملاء": "Customer service",
    "إذا لم يتم الرد، راسلوا على الرقم التالي. رجاءً من دون اتصال.": "If you do not receive a reply, message this number. Please do not call.",
    "مراسلة عبر واتساب": "Message on WhatsApp",
    "منتجاتنا": "Products",
    "من نحن": "About us",
    "موقعنا": "Location",
    "موقعنا الإلكتروني": "Website",
    "سلة المشتريات": "Shopping cart",
    "وجهتك للتسوق في دوما": "Your shopping destination in Douma",
    "كل احتياجاتك اليومية في مكان واحد": "Everyday essentials in one place",
    "قرطاسية، مكياج، هدايا، عطورات، ألعاب، طباعة ومستلزمات منزلية — أهلاً بك في مكتبة النصر.": "Stationery, makeup, gifts, perfumes, toys, printing and home essentials — welcome to Nasr Library.",
    "تصفّح الأقسام": "Browse categories",
    "اعثر علينا": "Find us",
    "ابحث عن منتج في قائمة الأسعار...": "Search products in the price list...",
    "ابحث عن منتج": "Search products",
    "البحث في قائمة الأسعار": "Search the price list",
    "تصفية قائمة الأسعار حسب الفئة": "Filter the price list by category",
    "تسوّق بسهولة": "Shop with ease",
    "أقسام مكتبة النصر": "Nasr Library departments",
    "اختر قسماً لعرض المنتجات المدرجة في قائمة الأسعار، أو اسألنا عن الأصناف المتوفرة.": "Choose a department to browse listed products, or ask us about current availability.",
    "الكل": "All",
    "الدفاتر": "Notebooks",
    "الأقلام": "Pens",
    "الألوان": "Art supplies",
    "أدوات مدرسية ومكتبية": "School & office supplies",
    "المطارات وعلب الطعام": "Bottles & lunch boxes",
    "الطباعة": "Printing",
    "طباعة": "Printing",
    "هدايا": "Gifts",
    "مستلزمات منزلية": "Home essentials",
    "الحقائب": "Bags",
    "قرطاسية": "Stationery",
    "مكياج": "Makeup",
    "عطورات": "Perfumes",
    "ألعاب": "Toys",
    "دفاتر وأقلام وأدوات مدرسية ومكتبية": "Notebooks, pens, and school and office supplies",
    "اسألنا عن الأصناف المتوفرة حالياً": "Ask about current availability",
    "تصفّح المنتجات": "Browse products",
    "أفكار وهدايا للمناسبات": "Gift ideas for special occasions",
    "استفسر عبر واتساب": "Ask on WhatsApp",
    "اسألنا عن الأنواع والأسعار الحالية": "Ask about current varieties and prices",
    "اسألنا عن الألعاب المتوفرة": "Ask about available toys",
    "خدمات طباعة مدرجة في قائمة الأسعار": "Printing services listed in the price list",
    "تصفّح الأسعار": "Browse prices",
    "أدوات واختيارات للمنزل": "Useful picks for your home",
    "رمز الموقع على الخريطة": "Map location code",
    "HC93+22W · دوما، سوريا": "HC93+22W · Douma, Syria",
    "زر مكتبة النصر": "Nasr Library location",
    "موقع مكتبة النصر الإلكتروني": "Nasr Library website",
    "افتح صفحتنا الرسمية على Google Sites": "Open our official Google Sites page",
    "قائمة الأسعار": "Price list",
    "منتجات متوفرة في القائمة": "Products in the price list",
    "الأسعار المعروضة قابلة للتغيير؛ تواصل معنا للتأكد من السعر والتوفر.": "Prices may change; contact us to confirm price and availability.",
    "حكايتنا ومكاننا": "Our story and location",
    "مكتبة النصر في سوريا، ريف دمشق، مدينة دوما، شارع الجامعة العربية.": "Nasr Library is on Al-Jami'a Al-Arabiya Street in Douma, Rural Damascus, Syria.",
    "نوفر لزبائننا قرطاسية ومكياجاً وهدايا وعطورات وألعاباً وخدمات طباعة ومستلزمات منزلية.": "We offer stationery, makeup, gifts, perfumes, toys, printing services and home essentials.",
    "تواصل معنا": "Contact us",
    "نرحب بزيارتكم": "We welcome your visit",
    "موقعنا على الخريطة": "Find us on the map",
    "شارع الجامعة العربية، دوما، ريف دمشق، سوريا": "Al-Jami'a Al-Arabiya Street, Douma, Rural Damascus, Syria",
    "افتح الموقع في خرائط Google": "Open in Google Maps",
    "اتصال هاتفي · 0954 672 747": "Call · 0954 672 747",
    "تواصل عبر واتساب": "Message us on WhatsApp",
    "مكتبة النصر · شارع الجامعة العربية · دوما، ريف دمشق": "Nasr Library · Al-Jami'a Al-Arabiya Street · Douma, Rural Damascus",
    "الأسعار قابلة للتغيير دون إشعار مسبق.": "Prices are subject to change without notice.",
    "جميع الحقوق محفوظة لدى Yusuf elbeses ©": "All rights reserved to Yusuf elbeses ©",
    "اسأل مساعد النصر": "Ask Nasr Assistant",
    "معلومات المكتبة فقط": "Library information only",
    "مساعد مكتبة النصر": "Nasr Library Assistant",
    "أهلاً بك! اسألني عن أقسام المكتبة، الأسعار المدرجة، العنوان أو موقعنا الإلكتروني.": "Welcome! Ask me about departments, listed prices, our address, or website.",
    "أسئلة مقترحة": "Suggested questions",
    "أقسام المنتجات": "Product departments",
    "عنوان المكتبة": "Library address",
    "الموقع الإلكتروني": "Website",
    "اكتب سؤالك عن مكتبة النصر": "Ask a question about Nasr Library",
    "اسأل عن مكتبة النصر...": "Ask about Nasr Library...",
    "إرسال": "Send",
    "إرسال السؤال": "Send question",
    "مساعد محلي يجيب من معلومات مكتبة النصر المتاحة فقط.": "Local assistant: answers only from available Nasr Library information.",
    "إغلاق المساعد": "Close assistant",
    "إغلاق السلة": "Close cart",
    "المجموع": "Total",
    "اسم الزبون (اختياري)": "Customer name (optional)",
    "مثال: أبو محمد": "Example: Alex",
    "تاريخ الفاتورة": "Invoice date",
    "ملاحظات (اختياري)": "Notes (optional)",
    "مثال: التسليم يوم الخميس": "Example: Deliver on Thursday",
    "مشاركة الفاتورة كملف PDF": "Share invoice as PDF",
    "سيحاول التطبيق فتح واتساب مباشرةً على رقم المكتبة وإرفاق PDF؛ إذا لم يدعم إصدار واتساب ذلك فاختر محادثة المكتبة من القائمة. وفي المتصفح اختر «حفظ كـPDF» من الطباعة.": "The app will try to open WhatsApp to the library number with the PDF attached. If unsupported, choose the library chat from the list. In a browser, choose Save as PDF when printing.",
    "تفريغ السلة": "Clear cart",
    "رقم الفاتورة:": "Invoice no.:",
    "التاريخ:": "Date:",
    "الزبون:": "Customer:",
    "المنتج": "Product",
    "الكمية": "Qty",
    "السعر": "Price",
    "الإجمالي": "Amount",
    "المجموع الكلي": "Grand total",
    "0 ل.س": "0 SYP",
    "ملاحظات:": "Notes:",
    "شكرًا لتعاملكم مع مكتبة النصر — دوما": "Thank you for shopping at Nasr Library — Douma",
    "أضف": "Add",
    "أُضيف ✓": "Added ✓",
    "لا توجد نتائج مطابقة للبحث.": "No matching products found.",
    "السلة فارغة حاليًا.": "Your cart is empty.",
    "حذف": "Remove",
    "السلة فارغة، أضف منتجات أولاً.": "Your cart is empty. Add products first.",
    "الفاتورة": "Invoice",
    "بدون اسم": "No name",
    "عدد الأصناف": "Items",
    "الإجمالي:": "Total:",
    "لا توجد عمليات سابقة.": "No previous transactions.",
    "أصناف أخرى": "more items",
    "العمليات المحفوظة محلياً": "transactions stored locally",
    "هل تريد مسح سجل العمليات السابقة من هذا الجهاز؟": "Clear the previous-transaction history from this device?",
    "يتبع في الصفحة التالية": "Continued on next page",
    "فاتورة مبيعات · دوما": "Sales invoice · Douma",
    "الإجمالي النهائي": "Grand total",
    "المبلغ المطلوب دفعه": "Amount due",
    "ملاحظات الطلب": "Order notes",
    "جميع الحقوق محفوظة لدى مكتبة النصر دوما": "All rights reserved to Nasr Library, Douma",
    "صفحة": "Page"
  };

  const CATEGORY_EN = {
    "الكل": "All",
    "مكياج": "Makeup",
    "عطورات": "Perfumes",
    "ألعاب": "Toys",
    "قرطاسية": "Stationery",
    "طباعة": "Printing",
    "الدفاتر": "Notebooks",
    "الأقلام": "Pens",
    "الألوان": "Art supplies",
    "أدوات مدرسية ومكتبية": "School & office supplies",
    "المطارات وعلب الطعام": "Bottles & lunch boxes",
    "الطباعة": "Printing",
    "هدايا": "Gifts",
    "مستلزمات منزلية": "Home essentials",
    "الحقائب": "Bags"
  };

  let language = "ar";
  try {
    const savedLanguage = localStorage.getItem(LANGUAGE_KEY);
    if (savedLanguage === "en" || savedLanguage === "ar") language = savedLanguage;
  } catch (_) {}

  const originalText = new WeakMap();
  const t = value => language === "en" ? (ENGLISH[value] || value) : value;
  const currency = () => language === "en" ? "SYP" : "ل.س";
  const category = value => language === "en" ? (CATEGORY_EN[value] || value) : value;

  function formatNumber(value) {
    const number = Number(value) || 0;
    return new Intl.NumberFormat(language === "en" ? "en-US" : "en-US", { maximumFractionDigits: 0 }).format(number);
  }

  function formatDate(timestamp) {
    const date = new Date(Number(timestamp) || Date.now());
    const locale = language === "en" ? "en-US-u-ca-gregory-nu-latn" : "ar-SY-u-ca-gregory-nu-latn";
    return new Intl.DateTimeFormat(locale, { year: "numeric", month: "short", day: "numeric", hour: "2-digit", minute: "2-digit" }).format(date);
  }

  function preserveWhitespace(source, replacement) {
    const leading = source.match(/^\s*/)?.[0] || "";
    const trailing = source.match(/\s*$/)?.[0] || "";
    return leading + replacement + trailing;
  }

  function translateTextNodes() {
    const walker = document.createTreeWalker(document.body, NodeFilter.SHOW_TEXT);
    let node;
    while ((node = walker.nextNode())) {
      if (!originalText.has(node)) originalText.set(node, node.nodeValue);
      const source = originalText.get(node);
      const trimmed = source.trim();
      if (!trimmed) continue;
      const replacement = language === "en" ? ENGLISH[trimmed] : null;
      node.nodeValue = replacement ? preserveWhitespace(source, replacement) : source;
    }
  }

  function setAttribute(selector, attribute, arabic, english) {
    const element = document.querySelector(selector);
    if (element) element.setAttribute(attribute, language === "en" ? english : arabic);
  }

  function translateAttributes() {
    setAttribute(".brand", "aria-label", "مكتبة النصر — الصفحة الرئيسية", "Nasr Library — Home");
    setAttribute("#settingsBtn", "aria-label", "الإعدادات", "Settings");
    setAttribute("#closeSettings", "aria-label", "إغلاق الإعدادات", "Close settings");
    setAttribute("#closeCart", "aria-label", "إغلاق السلة", "Close cart");
    setAttribute("#searchInput", "placeholder", "ابحث عن منتج في قائمة الأسعار...", "Search products in the price list...");
    setAttribute("#searchInput", "aria-label", "ابحث عن منتج", "Search products");
    setAttribute("#assistantPanel", "aria-label", "مساعد مكتبة النصر", "Nasr Library Assistant");
    setAttribute("#assistantClose", "aria-label", "إغلاق المساعد", "Close assistant");
    setAttribute("#assistantInput", "placeholder", "اسأل عن مكتبة النصر...", "Ask about Nasr Library...");
    setAttribute("#assistantInput", "aria-label", "اكتب سؤالك عن مكتبة النصر", "Ask a question about Nasr Library");
    setAttribute("#assistantForm button", "aria-label", "إرسال السؤال", "Send question");
    setAttribute(".assistant-suggestions", "aria-label", "أسئلة مقترحة", "Suggested questions");
    setAttribute(".settings-language", "aria-label", "اختيار اللغة", "Choose language");
    setAttribute("#customerName", "placeholder", "مثال: أبو محمد", "Example: Alex");
    setAttribute("#invoiceNotes", "placeholder", "مثال: التسليم يوم الخميس", "Example: Deliver on Thursday");

    const suggestions = language === "en"
      ? ["What departments do you have?", "Where is the library?", "What is your website?"]
      : ["ما أقسام المنتجات؟", "أين موقع المكتبة؟", "ما موقعكم الإلكتروني؟"];
    document.querySelectorAll(".assistant-suggestions [data-assistant-question]").forEach((button, index) => {
      if (suggestions[index]) button.dataset.assistantQuestion = suggestions[index];
    });
  }

  function readHistory() {
    try {
      const parsed = JSON.parse(localStorage.getItem(HISTORY_KEY) || "[]");
      return Array.isArray(parsed) ? parsed : [];
    } catch (_) {
      return [];
    }
  }

  function escapeHtml(value) {
    return String(value == null ? "" : value).replace(/[&<>"']/g, character => ({
      "&": "&amp;", "<": "&lt;", ">": "&gt;", "\"": "&quot;", "'": "&#39;"
    }[character]));
  }

  function renderHistory() {
    const list = document.getElementById("historyList");
    const count = document.getElementById("historyCount");
    if (!list || !count) return;
    const operations = readHistory().slice().reverse();
    count.textContent = String(operations.length);
    if (!operations.length) {
      list.innerHTML = `<div class="history-empty">${escapeHtml(t("لا توجد عمليات سابقة."))}</div>`;
      return;
    }
    list.innerHTML = operations.map(operation => {
      const items = Array.isArray(operation.items) ? operation.items : [];
      const itemList = items.slice(0, 8).map(item => `<li>${escapeHtml(item.name)} × ${escapeHtml(item.qty)}</li>`).join("");
      const more = items.length > 8 ? `<li>+ ${formatNumber(items.length - 8)} ${escapeHtml(t("أصناف أخرى"))}</li>` : "";
      const customer = operation.customer ? escapeHtml(operation.customer) : escapeHtml(t("بدون اسم"));
      return `<article class="history-operation">
        <div class="history-operation-head"><strong>${escapeHtml(t("الفاتورة"))} ${escapeHtml(operation.number || "—")}</strong><time>${escapeHtml(formatDate(operation.timestamp))}</time></div>
        <p>${escapeHtml(t("الزبون:"))} ${customer}</p>
        <p>${escapeHtml(t("الإجمالي:"))} ${formatNumber(operation.total)} ${escapeHtml(currency())} · ${formatNumber(items.length)} ${escapeHtml(t("عدد الأصناف"))}</p>
        ${itemList || more ? `<ul class="history-items">${itemList}${more}</ul>` : ""}
      </article>`;
    }).join("");
  }

  function saveOperation(invoice) {
    if (!invoice || !Array.isArray(invoice.items) || !invoice.items.length) return;
    const entry = {
      number: String(invoice.number || "").slice(0, 60),
      timestamp: Number(invoice.timestamp) || Date.now(),
      customer: String(invoice.customer || "").slice(0, 100),
      total: Number(invoice.total) || 0,
      items: invoice.items.slice(0, 100).map(item => ({
        name: String(item.name || "").slice(0, 120),
        qty: Math.max(1, Number(item.qty) || 1),
        price: Math.max(0, Number(item.price) || 0)
      }))
    };
    try {
      const operations = readHistory().filter(operation => operation.number !== entry.number);
      operations.push(entry);
      localStorage.setItem(HISTORY_KEY, JSON.stringify(operations.slice(-50)));
    } catch (_) {}
    renderHistory();
  }

  function closeSettings() {
    const overlay = document.getElementById("settingsOverlay");
    const button = document.getElementById("settingsBtn");
    if (!overlay) return;
    overlay.classList.remove("open");
    overlay.setAttribute("aria-hidden", "true");
    if (button) {
      button.setAttribute("aria-expanded", "false");
      button.focus();
    }
  }

  function setLanguage(nextLanguage, dispatch = true) {
    language = nextLanguage === "en" ? "en" : "ar";
    try { localStorage.setItem(LANGUAGE_KEY, language); } catch (_) {}
    document.documentElement.lang = language;
    document.documentElement.dir = language === "en" ? "ltr" : "rtl";
    document.body.classList.toggle("english-interface", language === "en");
    document.title = language === "en" ? "Nasr Library | Douma" : "مكتبة النصر | دوما";
    const description = document.querySelector('meta[name="description"]');
    if (description) description.content = language === "en"
      ? "Nasr Library in Douma, Rural Damascus. Stationery, gifts, printing and household products."
      : "مكتبة النصر في دوما، ريف دمشق. قرطاسية وهدايا وطباعة ومستلزمات متنوعة، مع قائمة أسعار ومشاركة الفاتورة عبر واتساب.";
    translateTextNodes();
    translateAttributes();
    document.querySelectorAll(".language-option").forEach(button => {
      const selected = button.dataset.language === language;
      button.classList.toggle("active", selected);
      button.setAttribute("aria-pressed", String(selected));
    });
    renderHistory();
    if (dispatch) window.dispatchEvent(new CustomEvent("nasr-language-change", { detail: { language } }));
  }

  document.getElementById("settingsBtn")?.addEventListener("click", () => {
    const overlay = document.getElementById("settingsOverlay");
    overlay.classList.add("open");
    overlay.setAttribute("aria-hidden", "false");
    document.getElementById("settingsBtn").setAttribute("aria-expanded", "true");
    document.getElementById("closeSettings").focus();
  });
  document.getElementById("closeSettings")?.addEventListener("click", closeSettings);
  document.getElementById("settingsOverlay")?.addEventListener("click", event => {
    if (event.target.id === "settingsOverlay") closeSettings();
  });
  document.getElementById("historyToggle")?.addEventListener("click", event => {
    const section = document.getElementById("historySection");
    section.hidden = !section.hidden;
    event.currentTarget.setAttribute("aria-expanded", String(!section.hidden));
    if (!section.hidden) renderHistory();
  });
  document.getElementById("clearHistory")?.addEventListener("click", () => {
    if (!window.confirm(t("هل تريد مسح سجل العمليات السابقة من هذا الجهاز؟"))) return;
    try { localStorage.removeItem(HISTORY_KEY); } catch (_) {}
    renderHistory();
  });
  document.querySelectorAll(".language-option").forEach(button => {
    button.addEventListener("click", () => setLanguage(button.dataset.language));
  });
  document.querySelector(".customer-service-link")?.addEventListener("click", closeSettings);
  document.addEventListener("keydown", event => {
    if (event.key === "Escape") closeSettings();
  });

  window.NasrSettings = {
    t,
    currency,
    category,
    formatDate,
    saveOperation,
    getLanguage: () => language,
    isEnglish: () => language === "en",
    getHistory: readHistory
  };

  setLanguage(language, false);
})();
