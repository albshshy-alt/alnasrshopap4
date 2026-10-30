/*
 * Browser-side deterrents only. They are not a security boundary: visitors can
 * disable JavaScript or inspect downloaded assets by other means.
 */
(() => {
  "use strict";

  const isEditable = target => target instanceof Element && (
    target.isContentEditable ||
    target.closest("input, textarea, select, [contenteditable='true']") !== null
  );

  document.addEventListener("keydown", event => {
    const key = String(event.key || "").toLowerCase();
    const modifier = event.ctrlKey || event.metaKey;
    const devtoolsShortcut =
      (event.ctrlKey && event.shiftKey && ["i", "j", "c", "k"].includes(key)) ||
      (modifier && event.altKey && ["i", "j", "c"].includes(key));
    const sourceShortcut = modifier && ["u", "s"].includes(key);
    const copyShortcut = modifier && key === "c" && !isEditable(event.target);

    if (key === "f12" || devtoolsShortcut || sourceShortcut || copyShortcut) {
      event.preventDefault();
      event.stopPropagation();
    }
  }, true);

  document.addEventListener("contextmenu", event => {
    if (!isEditable(event.target)) event.preventDefault();
  }, true);

  for (const eventName of ["copy", "cut"]) {
    document.addEventListener(eventName, event => {
      if (!isEditable(event.target)) event.preventDefault();
    }, true);
  }

  document.addEventListener("dragstart", event => {
    if (!isEditable(event.target)) event.preventDefault();
  }, true);
})();
