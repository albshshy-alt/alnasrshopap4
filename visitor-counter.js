(() => {
  const BADGE_ENDPOINT = "https://visitor-badge.laobi.icu/badge";
  const COUNTER_IDS = {
    total: "albshshy-alt.alnasrshopap4-total",
    instagram: "albshshy-alt.alnasrshopap4-instagram"
  };
  const COPY = {
    ar: {
      title: "عداد الزيارات",
      total: "إجمالي الزيارات",
      instagram: "زيارات عبر إنستغرام",
      note: "يستخدم الموقع خدمة عدّ خارجية لتقدير جلسات الزيارة؛ الأرقام ليست عدد الأشخاص أو عمليات الشراء. يظهر عداد إنستغرام عند فتح الرابط الموسوم.",
      totalAlt: "شارة إجمالي الزيارات",
      instagramAlt: "شارة زيارات إنستغرام"
    },
    en: {
      title: "Visit counter",
      total: "Total visits",
      instagram: "Visits via Instagram",
      note: "An external counter estimates browsing sessions, not people or purchases. The Instagram count appears when the tagged link is opened.",
      totalAlt: "Total visits badge",
      instagramAlt: "Instagram visits badge"
    },
    tr: {
      title: "Ziyaret sayacı",
      total: "Toplam ziyaret",
      instagram: "Instagram üzerinden ziyaret",
      note: "Harici sayaç yaklaşık tarama oturumlarını ölçer; kişi veya satın alma sayısı değildir. Instagram sayacı etiketli bağlantı açıldığında görünür.",
      totalAlt: "Toplam ziyaret rozeti",
      instagramAlt: "Instagram ziyaret rozeti"
    }
  };

  function firstCountInSession(key) {
    try {
      if (window.sessionStorage.getItem(key) === "1") return false;
      window.sessionStorage.setItem(key, "1");
    } catch (_) {
      // Count the page view if browser storage is unavailable.
    }
    return true;
  }

  function cameFromInstagram() {
    const params = new URLSearchParams(window.location.search);
    const source = (params.get("utm_source") || "").toLowerCase();
    if (source === "instagram" || source === "ig") return true;
    try {
      const host = new URL(document.referrer).hostname.toLowerCase();
      return host === "instagram.com" || host.endsWith(".instagram.com");
    } catch (_) {
      return false;
    }
  }

  function badgeUrl(counterId, shouldIncrement, label) {
    const params = new URLSearchParams({
      page_id: counterId,
      left_text: label,
      left_color: "24352a",
      right_color: "c9a24a",
      radius: "4",
      height: "24"
    });
    if (!shouldIncrement) params.set("query_only", "true");
    return `${BADGE_ENDPOINT}?${params.toString()}`;
  }

  function updateCopy(language) {
    const copy = COPY[language] || COPY.ar;
    document.querySelectorAll("[data-visitor-title]").forEach(node => { node.textContent = copy.title; });
    document.querySelectorAll("[data-visitor-label='total']").forEach(node => { node.textContent = copy.total; });
    document.querySelectorAll("[data-visitor-label='instagram']").forEach(node => { node.textContent = copy.instagram; });
    document.querySelectorAll("[data-visitor-note]").forEach(node => { node.textContent = copy.note; });
    document.querySelectorAll("[data-visitor-counter='total']").forEach(node => { node.alt = copy.totalAlt; });
    document.querySelectorAll("[data-visitor-counter='instagram']").forEach(node => { node.alt = copy.instagramAlt; });
  }

  function initialize() {
    const params = new URLSearchParams(window.location.search);
    const source = (params.get("utm_source") || "").toLowerCase();
    const instagramVisit = source === "instagram" || source === "ig" || cameFromInstagram();
    const counterView = params.get("counter_view") === "1";
    const showInstagramCounter = instagramVisit || counterView;
    const isPublishedSite = window.location.hostname === "albshshy-alt.github.io"
      && window.location.pathname.startsWith("/alnasrshopap4/");
    const totalIncrement = isPublishedSite && !counterView
      && firstCountInSession("nasr-library-total-visit-v1");
    const instagramIncrement = isPublishedSite && instagramVisit && !counterView
      && firstCountInSession("nasr-library-instagram-visit-v1");

    document.querySelectorAll("[data-visitor-counter='total']").forEach(image => {
      image.src = badgeUrl(COUNTER_IDS.total, totalIncrement, "visits");
    });
    document.querySelectorAll("[data-counter-row='instagram']").forEach(row => {
      row.hidden = !showInstagramCounter;
    });
    if (showInstagramCounter) {
      document.querySelectorAll("[data-visitor-counter='instagram']").forEach(image => {
        image.src = badgeUrl(COUNTER_IDS.instagram, instagramIncrement, "Instagram");
      });
    }

    const initialLanguage = window.NasrSettings?.getLanguage?.() || document.documentElement.lang || "ar";
    updateCopy(initialLanguage);
    window.addEventListener("nasr-language-change", event => updateCopy(event.detail?.language || "ar"));
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", initialize, { once: true });
  } else {
    initialize();
  }
})();
