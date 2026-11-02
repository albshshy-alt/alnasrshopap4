(() => {
  const BADGE_ENDPOINT = "https://visitor-badge.laobi.icu/badge";
  const COUNTER_IDS = {
    total: "albshshy-alt.alnasrshopap4-total",
    instagram: "albshshy-alt.alnasrshopap4-instagram"
  };

  function firstCountInSession(key) {
    try {
      if (window.sessionStorage.getItem(key) === "1") return false;
      window.sessionStorage.setItem(key, "1");
    } catch (_) {
      // Still count the visit when browser storage is unavailable.
    }
    return true;
  }

  function cameFromInstagram() {
    const source = (new URLSearchParams(window.location.search).get("utm_source") || "").toLowerCase();
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

  function initialize() {
    const isPublishedSite = window.location.hostname === "albshshy-alt.github.io"
      && window.location.pathname.startsWith("/alnasrshopap4/");
    const instagramVisit = cameFromInstagram();
    const totalIncrement = isPublishedSite && firstCountInSession("nasr-library-total-visit-v1");
    const instagramIncrement = isPublishedSite && instagramVisit
      && firstCountInSession("nasr-library-instagram-visit-v1");

    document.querySelectorAll("[data-visitor-counter='total']").forEach(image => {
      image.src = badgeUrl(COUNTER_IDS.total, totalIncrement, "visits");
    });
    document.querySelectorAll("[data-counter-row='instagram']").forEach(row => {
      row.hidden = !instagramVisit;
    });
    if (instagramVisit) {
      document.querySelectorAll("[data-visitor-counter='instagram']").forEach(image => {
        image.src = badgeUrl(COUNTER_IDS.instagram, instagramIncrement, "Instagram");
      });
    }
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", initialize, { once: true });
  } else {
    initialize();
  }
})();
