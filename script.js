// ===== بيانات المنتجات =====
let products = [
  // الدفاتر
  { category: "الدفاتر", name: "دفتر 100 طبق سلك", price: 60 },
  { category: "الدفاتر", name: "دفتر 100 طبق عادي", price: 35 },
  { category: "الدفاتر", name: "دفتر عادي 50 طبق", price: 25 },
  { category: "الدفاتر", name: "دفتر سلك 140 طبق صباغ", price: 150 },
  { category: "الدفاتر", name: "دفتر سلك 140 طبق عادي", price: 130 },
  { category: "الدفاتر", name: "دفتر سلك 200 طبق", price: 200 },
  { category: "الدفاتر", name: "دفتر 100 طبق كرتون", price: 70 },
  { category: "الدفاتر", name: "دفتر ملاحظات صغير", price: 50 },
  { category: "الدفاتر", name: "دفتر ملاحظات أسود", price: 30 },
  { category: "الدفاتر", name: "دفتر رسم كبير", price: 80 },
  { category: "الدفاتر", name: "دفتر رسم صغير", price: 50 },
  { category: "الدفاتر", name: "دفتر مقسم جامعي وسط", price: 180 },
  { category: "الدفاتر", name: "دفتر مقسم جامعي كبير", price: 250 },
  { category: "الدفاتر", name: "دفتر سلك 180", price: 150 },
  { category: "الدفاتر", name: "دفتر الذكريات مع قفل", price: 250 },
  { category: "الدفاتر", name: "دفتر الذكريات عادي", price: 200 },
  { category: "الدفاتر", name: "دفتر 60", price: 50 },
  { category: "الدفاتر", name: "دفتر سلك اديب 70", price: 100 },
  { category: "الدفاتر", name: "دفتر سلك 180 صباغ وسط", price: 180 },
  { category: "الدفاتر", name: "دفتر رسم كانسون فاخر", price: 150 },
  { category: "الدفاتر", name: "دفتر مع مسند ورقة", price: 70 },
  { category: "الدفاتر", name: "دفتر هدى", price: 50 },
  { category: "الدفاتر", name: "دفتر ملاحظات صغير - نوع 2", price: 25 },
  { category: "الدفاتر", name: "دفتر ملاحظات كبير", price: 35 },

  // الأقلام
  { category: "الأقلام", name: "قلم ليكسي", price: 20 },
  { category: "الأقلام", name: "قلم زيرو", price: 20 },
  { category: "الأقلام", name: "قلم هاوزر", price: 20 },
  { category: "الأقلام", name: "قلم عادي", price: 10 },
  { category: "الأقلام", name: "هايلايتر", price: 25 },
  { category: "الأقلام", name: "قلم كباس", price: 25 },
  { category: "الأقلام", name: "قلم لوح", price: 25 },
  { category: "الأقلام", name: "قلم تخطيط", price: 30 },
  { category: "الأقلام", name: "قلم حبر", price: 70 },
  { category: "الأقلام", name: "قلم مع شكلة", price: 150 },
  { category: "الأقلام", name: "قلم علامة", price: 30 },
  { category: "الأقلام", name: "أقلام تعليم سكاكر", price: 150 },
  { category: "الأقلام", name: "قلم ديلي أربع ألوان", price: 70 },
  { category: "الأقلام", name: "قلم كباس زيرو", price: 65 },
  { category: "الأقلام", name: "قلم كباس زيرو - نوع 2", price: 35 },
  { category: "الأقلام", name: "قلم رصاص زيرو", price: 15 },
  { category: "الأقلام", name: "قلم رصاص شكل", price: 25 },
  { category: "الأقلام", name: "قلم ديلي ون سبونج", price: 40 },
  { category: "الأقلام", name: "قلم طرفين", price: 25 },

  // الألوان
  { category: "الألوان", name: "Deli أربعة ألوان", price: 60 },
  { category: "الألوان", name: "ألوان 10 ألوان", price: 100 },
  { category: "الألوان", name: "هاوزر 12 لون", price: 300 },
  { category: "الألوان", name: "Deli 12 لون", price: 200 },
  { category: "الألوان", name: "Doms فلوماستر 12 لون", price: 100 },
  { category: "الألوان", name: "ألوان 12 لون", price: 130 },
  { category: "الألوان", name: "زدني 12 لون صغير", price: 50 },
  { category: "الألوان", name: "زدني 12 لون كبير", price: 100 },
  { category: "الألوان", name: "هاوزر", price: 200 },
  { category: "الألوان", name: "فلوماستر صغير 12 لون", price: 50 },
  { category: "الألوان", name: "ألوان زيرو صغير", price: 80 },
  { category: "الألوان", name: "ألوان زيرو وسط", price: 160 },
  { category: "الألوان", name: "ألوان زيرو 36 لون", price: 500 },
  { category: "الألوان", name: "ألوان بنسل", price: 100 },
  { category: "الألوان", name: "ألوان مائية كبيرة", price: 300 },
  { category: "الألوان", name: "ألوان مائية صغيرة", price: 150 },
  { category: "الألوان", name: "ألوان فلوماستر نوع 2", price: 50 },

  // أدوات مدرسية ومكتبية
  { category: "أدوات مدرسية ومكتبية", name: "كلكتر كبير", price: 50 },
  { category: "أدوات مدرسية ومكتبية", name: "كلكتر وسط", price: 45 },
  { category: "أدوات مدرسية ومكتبية", name: "كلكتر صغير", price: 25 },
  { category: "أدوات مدرسية ومكتبية", name: "هندسة زيرو", price: 150 },
  { category: "أدوات مدرسية ومكتبية", name: "هندسة عادي", price: 80 },
  { category: "أدوات مدرسية ومكتبية", name: "ممحاة زيرو", price: 5 },
  { category: "أدوات مدرسية ومكتبية", name: "مبراة", price: 10 },
  { category: "أدوات مدرسية ومكتبية", name: "ممحاة", price: 15, note: "متوسط سعر النطاق 10–20" },
  { category: "أدوات مدرسية ومكتبية", name: "كبسات", price: 40 },
  { category: "أدوات مدرسية ومكتبية", name: "مكعب ورق", price: 80 },
  { category: "أدوات مدرسية ومكتبية", name: "ستاند ورق", price: 70 },
  { category: "أدوات مدرسية ومكتبية", name: "ستاند أقلام", price: 100 },
  { category: "أدوات مدرسية ومكتبية", name: "لاصق", price: 30 },
  { category: "أدوات مدرسية ومكتبية", name: "مقلمة سندويشة", price: 80 },
  { category: "أدوات مدرسية ومكتبية", name: "طبق ايفا", price: 15 },
  { category: "أدوات مدرسية ومكتبية", name: "تاب 8.5 انش", price: 200 },
  { category: "أدوات مدرسية ومكتبية", name: "تاب 12 انش", price: 350 },
  { category: "أدوات مدرسية ومكتبية", name: "تاب 10 انش", price: 250 },
  { category: "أدوات مدرسية ومكتبية", name: "مقلمة ليكسي", price: 350 },
  { category: "أدوات مدرسية ومكتبية", name: "مقلمة آلة حاسبة", price: 200 },
  { category: "أدوات مدرسية ومكتبية", name: "ممحاة 4 أشكال", price: 25 },
  { category: "أدوات مدرسية ومكتبية", name: "مقلمة ثقيلة", price: 500 },
  { category: "أدوات مدرسية ومكتبية", name: "لاصق 8 غرام", price: 50 },
  { category: "أدوات مدرسية ومكتبية", name: "مقلمة اسطوانة", price: 100 },
  { category: "أدوات مدرسية ومكتبية", name: "مقلمة سحاب", price: 100 },
  { category: "أدوات مدرسية ومكتبية", name: "3 برايات", price: 100 },
  { category: "أدوات مدرسية ومكتبية", name: "ممحاة بوري", price: 60 },
  { category: "أدوات مدرسية ومكتبية", name: "ممحاة زيرو - نوع 2", price: 20 },
  { category: "أدوات مدرسية ومكتبية", name: "ممحاة كلاس", price: 10 },
  { category: "أدوات مدرسية ومكتبية", name: "ممحاة كلاس ملونة", price: 20 },
  { category: "أدوات مدرسية ومكتبية", name: "ممحاة مابيد", price: 20 },
  { category: "أدوات مدرسية ومكتبية", name: "علبة أقلام مثلث", price: 100 },
  { category: "أدوات مدرسية ومكتبية", name: "ممحاة آرثر", price: 10 },
  { category: "أدوات مدرسية ومكتبية", name: "مجموعة جوهرة", price: 100 },
  { category: "أدوات مدرسية ومكتبية", name: "ممحاة لون", price: 20 },
  { category: "أدوات مدرسية ومكتبية", name: "مبراة لابوبو", price: 25 },
  { category: "أدوات مدرسية ومكتبية", name: "ممحاة دومس", price: 5 },
  { category: "أدوات مدرسية ومكتبية", name: "مبراة مابيد", price: 15 },
  { category: "أدوات مدرسية ومكتبية", name: "مجموعة هاوزر", price: 250 },
  { category: "أدوات مدرسية ومكتبية", name: "مجموعة هاوزر كبيرة", price: 350 },
  { category: "أدوات مدرسية ومكتبية", name: "برو", price: 10 },
  { category: "أدوات مدرسية ومكتبية", name: "مسطرة طرفين", price: 25 },
  { category: "أدوات مدرسية ومكتبية", name: "طبق صور", price: 10 },
  { category: "أدوات مدرسية ومكتبية", name: "طبق صور صغير", price: 5 },
  { category: "أدوات مدرسية ومكتبية", name: "مبراة كلاسيك بنسل", price: 10 },
  { category: "أدوات مدرسية ومكتبية", name: "مجموعة ألوان كلارو", price: 180 },
  { category: "أدوات مدرسية ومكتبية", name: "ألوان كايزو صغيرة", price: 60 },
  { category: "أدوات مدرسية ومكتبية", name: "علبة أقلام رصاص مثلث أصلي", price: 150 },
  { category: "أدوات مدرسية ومكتبية", name: "علبة أقلام رصاص زيرو", price: 150 },
  { category: "أدوات مدرسية ومكتبية", name: "براية ثقيلة", price: 50 },
  { category: "أدوات مدرسية ومكتبية", name: "كارتيبل خشب", price: 100 },
  { category: "أدوات مدرسية ومكتبية", name: "كارتيبل ملون", price: 80 },
  { category: "أدوات مدرسية ومكتبية", name: "كلكتر شطب", price: 60 },

  // المطارات وعلب الطعام
  { category: "المطارات وعلب الطعام", name: "مطرة بفلة", price: 1000 },
  { category: "المطارات وعلب الطعام", name: "Lucky Caffe", price: 500 },
  { category: "المطارات وعلب الطعام", name: "مطرة أسود صغير", price: 450 },
  { category: "المطارات وعلب الطعام", name: "مطرة بطيخة", price: 400 },
  { category: "المطارات وعلب الطعام", name: "مطرة طوي", price: 300 },
  { category: "المطارات وعلب الطعام", name: "مطرة قزاز", price: 180 },
  { category: "المطارات وعلب الطعام", name: "مطرة بلاستيك", price: 150 },
  { category: "المطارات وعلب الطعام", name: "مطرة دولاب", price: 400 },
  { category: "المطارات وعلب الطعام", name: "Work Hard", price: 350 },
  { category: "المطارات وعلب الطعام", name: "مطرة عادية", price: 150 },
  { category: "المطارات وعلب الطعام", name: "مطرة زهر", price: 360 },
  { category: "المطارات وعلب الطعام", name: "علبة طعام", price: 200 },
  { category: "المطارات وعلب الطعام", name: "علبة طعام مع مطرة", price: 500 },
  { category: "المطارات وعلب الطعام", name: "علبة طعام - نوع 2", price: 180 },
  { category: "المطارات وعلب الطعام", name: "علبة طعام - نوع 3", price: 150 },
  { category: "المطارات وعلب الطعام", name: "مطرة سبورت", price: 450 },
  { category: "المطارات وعلب الطعام", name: "مطرة شكل سيارة", price: 500 },
  { category: "المطارات وعلب الطعام", name: "مطرة عادي - نوع 2", price: 180 },
  { category: "المطارات وعلب الطعام", name: "مطرة مع كوبين", price: 450 },

  // الطباعة
  { category: "الطباعة", name: "طباعة ملونة (للصفحة)", price: 10 },
  { category: "الطباعة", name: "طباعة أبيض وأسود (للصفحة)", price: 5 },

  // هدايا
  { category: "هدايا", name: "لمبدير جوز", price: 1300 },
  { category: "هدايا", name: "لمبدير ورد", price: 350 },
  { category: "هدايا", name: "لمبدير صغير", price: 250 },
  { category: "هدايا", name: "لمبدير صمدية", price: 450 },
  { category: "هدايا", name: "لمبدير دراسة", price: 650 },
  { category: "هدايا", name: "سماعة", price: 600 },
  { category: "هدايا", name: "سماعة ب47", price: 450 },
  { category: "هدايا", name: "آيات قرآنية", price: 350 },
  { category: "هدايا", name: "آية الكرسي", price: 300 },
  { category: "هدايا", name: "عبارات قرآنية", price: 200 },
  { category: "هدايا", name: "لوحات منزلية", price: 250 },
  { category: "هدايا", name: "دبدوب صغير", price: 1000 },
  { category: "هدايا", name: "دبدوب وسط", price: 1750 },
  { category: "هدايا", name: "دبدوب كبير", price: 2750 },
  { category: "هدايا", name: "دباديب", price: 500 },
  { category: "هدايا", name: "جوز كوب أحمر", price: 500 },
  { category: "هدايا", name: "رسالة البحر", price: 100 },
  { category: "هدايا", name: "جوز شجرة صغير", price: 150 },
  { category: "هدايا", name: "قرآن وسط", price: 450 },
  { category: "هدايا", name: "كلاب وأرانب", price: 350 },
  { category: "هدايا", name: "متر سلوفان", price: 5 },
  { category: "هدايا", name: "ستاند محارم", price: 200 },
  { category: "هدايا", name: "ستاند محارم كبير", price: 250 },
  { category: "هدايا", name: "متر تجاليد", price: 60 },
  { category: "هدايا", name: "ساعة", price: 500 },
  { category: "هدايا", name: "ساعة صغيرة", price: 350 },
  { category: "هدايا", name: "دولاب مقاس 50", price: 120 },
  { category: "هدايا", name: "دولاب مقاس وسط", price: 150 },
  { category: "هدايا", name: "دولاب مقاس كبير", price: 200 },
  { category: "هدايا", name: "سترة سباحة", price: 280 },
  { category: "هدايا", name: "مسبحة", price: 150 },
  { category: "هدايا", name: "طوق أحرف", price: 120 },
  { category: "هدايا", name: "مبخرة", price: 250 },
  { category: "هدايا", name: "أحرف عيد ميلاد", price: 50 },
  { category: "هدايا", name: "منبه", price: 350 },
  { category: "هدايا", name: "شمع كبير", price: 35 },
  { category: "هدايا", name: "شمع صغير", price: 25 },
  { category: "هدايا", name: "بالون هابي بيرثداي", price: 250 },
  { category: "هدايا", name: "بخاخ ثلج صغير", price: 100 },
  { category: "هدايا", name: "بخاخ ثلج كبير", price: 180 },
  { category: "هدايا", name: "جزدان", price: 150 },
  { category: "هدايا", name: "طبق تغليف", price: 35 },
  { category: "هدايا", name: "أسوارة علم سوريا", price: 70 },
  { category: "هدايا", name: "فراشات", price: 80 },
  { category: "هدايا", name: "مراية - نوع 1", price: 50 },
  { category: "هدايا", name: "كيس كرتون صغير", price: 50 },
  { category: "هدايا", name: "كيس كرتون وسط", price: 80 },
  { category: "هدايا", name: "مراية - نوع 2", price: 150 },
  { category: "هدايا", name: "مراية - نوع 3", price: 70 },
  { category: "هدايا", name: "لحشة - نوع 1", price: 150 },
  { category: "هدايا", name: "لحشة - نوع 2", price: 250 },
  { category: "هدايا", name: "لحشات (طقم)", price: 350 },
  { category: "هدايا", name: "حجاب", price: 350 },
  { category: "هدايا", name: "إشارة", price: 450 },
  { category: "هدايا", name: "خاتم", price: 70 },
  { category: "هدايا", name: "منبه أخضر", price: 400 },
  { category: "هدايا", name: "منبه أزرق", price: 400 },
  { category: "هدايا", name: "منبه موديل كاسة", price: 300 },
  { category: "هدايا", name: "منبه أزرق غامق", price: 650 },
  { category: "هدايا", name: "منبه بني صغير", price: 300 },
  { category: "هدايا", name: "منبه أخضر مستطيل", price: 350 },
  { category: "هدايا", name: "دبدوب غطاء كبير", price: 600 },
  { category: "هدايا", name: "دبدوب غطاء صغير", price: 350 },

  // مستلزمات منزلية
  { category: "مستلزمات منزلية", name: "إبريق شرب مع 7 كاسات", price: 1250 },
  { category: "مستلزمات منزلية", name: "إبريق شرب مع 7 كاسات (تقيل)", price: 1650 },
  { category: "مستلزمات منزلية", name: "فناجين قهوة", price: 1400 },
  { category: "مستلزمات منزلية", name: "فناجين قهوة أبيض", price: 750 },
  { category: "مستلزمات منزلية", name: "كاسات شرب", price: 500 },
  { category: "مستلزمات منزلية", name: "زبادي العسلي", price: 2500 },
  { category: "مستلزمات منزلية", name: "جوز سكرية", price: 350 },
  { category: "مستلزمات منزلية", name: "كوب بني", price: 180 },
  { category: "مستلزمات منزلية", name: "كوب سمايل", price: 150 },
  { category: "مستلزمات منزلية", name: "كوب وردي", price: 200 },
  { category: "مستلزمات منزلية", name: "زبادي صغيرة", price: 500 },
  { category: "مستلزمات منزلية", name: "كاسات شاي تقيلة", price: 450 },
  { category: "مستلزمات منزلية", name: "سكرية", price: 250 },
  { category: "مستلزمات منزلية", name: "كاسات شاي مع صحون", price: 1300 },
  { category: "مستلزمات منزلية", name: "جوز كوب قهوة", price: 350 },
  { category: "مستلزمات منزلية", name: "كوب أحمر", price: 250 },
  { category: "مستلزمات منزلية", name: "جوز كوب عسلي", price: 450 },
  { category: "مستلزمات منزلية", name: "كوب كبير", price: 400 },
  { category: "مستلزمات منزلية", name: "كاسة متة", price: 250 },
  { category: "مستلزمات منزلية", name: "كاسات", price: 250 },
  { category: "مستلزمات منزلية", name: "كوب أرنب", price: 250 },
  { category: "مستلزمات منزلية", name: "كوب حراري كبير", price: 100, note: "مورد" },
  { category: "مستلزمات منزلية", name: "كوب حراري صغير", price: 70 },
  { category: "مستلزمات منزلية", name: "كاسات شاي كبير", price: 450 },
  { category: "مستلزمات منزلية", name: "زبادي", price: 500 },
  { category: "مستلزمات منزلية", name: "6 كاسات شاي عادية", price: 250 },
  { category: "مستلزمات منزلية", name: "12 كاسة شاي", price: 300 },
  { category: "مستلزمات منزلية", name: "صينية صغيرة", price: 180 },
  { category: "مستلزمات منزلية", name: "صينية كبيرة", price: 250 },

  // الحقائب
  { category: "الحقائب", name: "حقيبة كأس العالم", price: 1000 },
  { category: "الحقائب", name: "حقيبة لابتوب", price: 3000 },
  { category: "الحقائب", name: "حقيبة كبيرة", price: 2500 },
  { category: "الحقائب", name: "حقيبة صغيرة", price: 500 },
  { category: "الحقائب", name: "حقيبة العادية", price: 850 },
  { category: "الحقائب", name: "حقيبة مارشميلو", price: 2250 },
  { category: "الحقائب", name: "حقيبة صينية", price: 2250 },
  { category: "الحقائب", name: "حقيبة جرار", price: 3000 },
  { category: "الحقائب", name: "حقيبة مقاس صغير", price: 850 },
  { category: "الحقائب", name: "حقيبة سكول باص", price: 1130 },
  { category: "الحقائب", name: "حقيبة بوما", price: 1150 },
  { category: "الحقائب", name: "حقيبة أوبوفان", price: 2250 },
  { category: "الحقائب", name: "حقيبة موديل 162", price: 1750 },
  { category: "الحقائب", name: "حقيبة موديل 199", price: 2250 },
];

products.forEach((p, i) => (p.id = i));

let categories = [...new Set(products.map(p => p.category))];

try { localStorage.removeItem("nasrLibraryInvoices"); } catch {}
let activeCategory = "الكل";
let cart = {}; // { id: { ...product, qty } }

const catalogEl = document.getElementById("catalog");
const tabsEl = document.getElementById("tabs");
const searchInput = document.getElementById("searchInput");
const cartBtn = document.getElementById("cartBtn");
const cartCount = document.getElementById("cartCount");
const cartOverlay = document.getElementById("cartOverlay");
const closeCart = document.getElementById("closeCart");
const cartItemsEl = document.getElementById("cartItems");
const cartTotalEl = document.getElementById("cartTotal");
const clearCartBtn = document.getElementById("clearCart");
const shareInvoiceBtn = document.getElementById("shareInvoiceBtn");
const customerNameInput = document.getElementById("customerName");
const invoiceDateInput = document.getElementById("invoiceDateInput");
(function setDefaultInvoiceDate() {
  const d = new Date();
  invoiceDateInput.value = `${d.getFullYear()}-${String(d.getMonth()+1).padStart(2,"0")}-${String(d.getDate()).padStart(2,"0")}`;
})();
const invoiceNotesInput = document.getElementById("invoiceNotes");

// ===== التبويبات =====
function renderTabs() {
  const allTabs = ["الكل", ...categories];
  tabsEl.innerHTML = allTabs.map(cat => `
    <button class="tab-btn ${cat === activeCategory ? "active" : ""}" data-cat="${cat}">${cat}</button>
  `).join("");

  tabsEl.querySelectorAll(".tab-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      activeCategory = btn.dataset.cat;
      renderTabs();
      renderCatalog();
    });
  });
}

// ===== لون كعب الكتاب لكل تصنيف =====
const SPINE_COLORS = ["#8a3b32", "#1f3a2e", "#a9812f", "#4a5d7a", "#6d4a73", "#3f7a72", "#b2623a"];
function spineColor(cat) {
  const i = Math.max(0, categories.indexOf(cat));
  return SPINE_COLORS[i % SPINE_COLORS.length];
}

// ===== الكتالوج =====
function renderCatalog() {
  const query = searchInput.value.trim().toLowerCase();
  const filtered = products.filter(p => {
    const matchesCategory = activeCategory === "الكل" || p.category === activeCategory;
    const matchesSearch = p.name.toLowerCase().includes(query);
    return matchesCategory && matchesSearch;
  });

  if (filtered.length === 0) {
    catalogEl.innerHTML = `<p class="no-results">لا توجد نتائج مطابقة للبحث.</p>`;
    return;
  }

  const grouped = {};
  filtered.forEach(p => {
    if (!grouped[p.category]) grouped[p.category] = [];
    grouped[p.category].push(p);
  });

  catalogEl.innerHTML = Object.entries(grouped).map(([cat, items]) => `
    <div class="cat-group" style="--spine:${spineColor(cat)}">
      <h2>${cat} <span class="cat-count">${items.length}</span></h2>
      <div class="cat-grid">
        ${items.map(p => `
          <div class="product-card">
            <h4>${p.name}</h4>
            ${p.note ? `<div class="product-note">${p.note}</div>` : ""}
            <div class="product-bottom">
              <span class="price">${p.price} <small>ل.س</small></span>
              <button class="add-btn" data-id="${p.id}">أضف</button>
            </div>
          </div>
        `).join("")}
      </div>
    </div>
  `).join("");

  catalogEl.querySelectorAll(".add-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      addToCart(parseInt(btn.dataset.id));
      btn.textContent = "أُضيف ✓";
      btn.classList.add("added");
      setTimeout(() => {
        btn.textContent = "أضف";
        btn.classList.remove("added");
      }, 900);
    });
  });
}

// ===== السلة =====
function addToCart(id) {
  const product = products.find(p => p.id === id);
  if (!product) return;
  if (cart[id]) {
    cart[id].qty += 1;
  } else {
    cart[id] = { ...product, qty: 1 };
  }
  updateCartUI();
}

function changeQty(id, delta) {
  if (!cart[id]) return;
  cart[id].qty += delta;
  if (cart[id].qty <= 0) delete cart[id];
  updateCartUI();
}

function removeItem(id) {
  delete cart[id];
  updateCartUI();
}

function getCartSubtotal() {
  return Object.values(cart).reduce((sum, i) => sum + i.qty * i.price, 0);
}

function updateCartUI() {
  const items = Object.values(cart);
  const totalQty = items.reduce((sum, i) => sum + i.qty, 0);
  const totalPrice = getCartSubtotal();

  if (cartCount.textContent !== String(totalQty)) {
    cartCount.classList.remove("bump");
    void cartCount.offsetWidth;
    cartCount.classList.add("bump");
  }
  cartCount.textContent = totalQty;
  cartTotalEl.textContent = `${totalPrice} ل.س`;

  if (items.length === 0) {
    cartItemsEl.innerHTML = `<p class="empty-cart">السلة فارغة حاليًا.</p>`;
    return;
  }

  cartItemsEl.innerHTML = items.map(i => `
    <div class="cart-item">
      <span class="cart-item-name">${i.name}</span>
      <div class="cart-item-qty">
        <button class="qty-btn" data-id="${i.id}" data-delta="-1">−</button>
        <span>${i.qty}</span>
        <button class="qty-btn" data-id="${i.id}" data-delta="1">+</button>
      </div>
      <span>${i.qty * i.price} ل.س</span>
      <button class="remove-btn" data-id="${i.id}" title="حذف">×</button>
    </div>
  `).join("");

  cartItemsEl.querySelectorAll(".qty-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      changeQty(parseInt(btn.dataset.id), parseInt(btn.dataset.delta));
    });
  });
  cartItemsEl.querySelectorAll(".remove-btn").forEach(btn => {
    btn.addEventListener("click", () => removeItem(parseInt(btn.dataset.id)));
  });
}

// ===== التاريخ =====
// تنسيق ثابت: تقويم ميلادي + أرقام إنجليزية، حتى لا يتغيّر الشهر/اليوم بحسب لغة الجهاز
function formatInvoiceDate(date) {
  return new Intl.DateTimeFormat("ar-SY-u-ca-gregory-nu-latn", {
    year: "numeric", month: "long", day: "numeric"
  }).format(date);
}

// ===== الفاتورة والطباعة =====
function generateInvoiceNumber() {
  const now = new Date();
  return `NS-${now.getFullYear()}${(now.getMonth()+1).toString().padStart(2,"0")}${now.getDate().toString().padStart(2,"0")}-${now.getHours()}${now.getMinutes()}${now.getSeconds()}`;
}

function fillInvoiceDOM(invoice) {
  document.getElementById("invNumber").textContent = invoice.number;
  document.getElementById("invDate").textContent = invoice.timestamp ? formatInvoiceDate(new Date(invoice.timestamp)) : invoice.dateLabel;
  document.getElementById("invCustomer").textContent = invoice.customer || "—";

  const notesBox = document.getElementById("invNotesBox");
  if (invoice.notes) {
    document.getElementById("invNotes").textContent = invoice.notes;
    notesBox.classList.add("show");
  } else {
    notesBox.classList.remove("show");
  }

  document.getElementById("invoiceBody").innerHTML = invoice.items.map(i => `
    <tr>
      <td>${i.name}</td>
      <td>${i.qty}</td>
      <td>${i.price} ل.س</td>
      <td>${i.qty * i.price} ل.س</td>
    </tr>
  `).join("");

  document.getElementById("invTotal").textContent = `${invoice.total} ل.س`;
}

function buildInvoiceFromCart() {
  const items = Object.values(cart).map(i => ({
    name: i.name, qty: i.qty, price: i.price
  }));
  const total = getCartSubtotal();
  const customer = customerNameInput.value.trim();
  const notes = invoiceNotesInput.value.trim();
  let now = new Date();
  if (invoiceDateInput.value) {
    const [y, m, d] = invoiceDateInput.value.split("-").map(Number);
    now = new Date(y, m - 1, d, now.getHours(), now.getMinutes(), now.getSeconds());
  }

  const invoice = {
    number: generateInvoiceNumber(),
    dateLabel: formatInvoiceDate(now),
    timestamp: now.getTime(),
    customer,
    notes,
    items,
    total
  };

  fillInvoiceDOM(invoice);
  return invoice;
}

function formatInvoiceForWhatsApp(invoice) {
  const lines = [
    "فاتورة مكتبة النصر — دوما",
    `رقم الفاتورة: ${invoice.number}`,
    `التاريخ: ${invoice.dateLabel}`
  ];

  if (invoice.customer) lines.push(`الزبون: ${invoice.customer}`);
  lines.push("", "المنتجات:");
  invoice.items.forEach((item, index) => {
    lines.push(`${index + 1}. ${item.name} × ${item.qty} = ${item.qty * item.price} ل.س`);
  });
  lines.push("", `الإجمالي: ${invoice.total} ل.س`);
  if (invoice.notes) lines.push(`ملاحظات: ${invoice.notes}`);
  lines.push("", "مكتبة النصر — 0954 672 747");
  return lines.join("\n");
}

shareInvoiceBtn.addEventListener("click", () => {
  if (Object.keys(cart).length === 0) {
    alert("السلة فارغة، أضف منتجات أولاً.");
    return;
  }
  const invoice = buildInvoiceFromCart();
  const message = formatInvoiceForWhatsApp(invoice);
  const whatsappUrl = `https://wa.me/963954672747?text=${encodeURIComponent(message)}`;
  window.open(whatsappUrl, "_blank", "noopener,noreferrer");
});

// ===== أحداث عامة =====
searchInput.addEventListener("input", renderCatalog);
cartBtn.addEventListener("click", () => cartOverlay.classList.add("open"));
closeCart.addEventListener("click", () => cartOverlay.classList.remove("open"));
cartOverlay.addEventListener("click", (e) => {
  if (e.target === cartOverlay) cartOverlay.classList.remove("open");
});
clearCartBtn.addEventListener("click", () => {
  cart = {};
  invoiceNotesInput.value = "";
  updateCartUI();
});

// ===== بطاقات أقسام المكتبة =====
document.querySelectorAll("[data-open-category]").forEach(button => {
  button.addEventListener("click", () => {
    const requestedCategory = button.dataset.openCategory;
    activeCategory = categories.includes(requestedCategory) ? requestedCategory : "الكل";
    searchInput.value = "";
    renderTabs();
    renderCatalog();
    document.getElementById("priceHeading").scrollIntoView({ behavior: "smooth", block: "start" });
  });
});

document.querySelectorAll("[data-ask-category]").forEach(button => {
  button.addEventListener("click", () => {
    const category = button.dataset.askCategory;
    const message = encodeURIComponent(`مرحباً، أريد الاستفسار عن قسم ${category} والأصناف المتوفرة والأسعار في مكتبة النصر.`);
    window.open(`https://wa.me/963954672747?text=${message}`, "_blank", "noopener,noreferrer");
  });
});

// ===== مساعد محلي محدود بمعلومات مكتبة النصر =====
function normalizeLibraryQuestion(value) {
  return String(value || "").toLowerCase()
    .replace(/[\u064B-\u065F\u0670\u0640]/g, "")
    .replace(/[أإآ]/g, "ا").replace(/ى/g, "ي").replace(/ة/g, "ه")
    .replace(/[^\u0600-\u06FFa-z0-9\s]/gi, " ").replace(/\s+/g, " ").trim();
}

function libraryQuestionHas(question, phrases) {
  return phrases.some(phrase => question.includes(normalizeLibraryQuestion(phrase)));
}

function answerLibraryQuestion(rawQuestion) {
  const question = normalizeLibraryQuestion(rawQuestion);
  const mapsLink = "https://www.google.com/maps/search/?api=1&query=HC93%2B22W%2C%20Douma%2C%20Syria";
  const websiteLink = "https://sites.google.com/view/al-nasr-bookstore/%D8%A7%D9%84%D8%B5%D9%81%D8%AD%D8%A9-%D8%A7%D9%84%D8%B1%D8%A6%D9%8A%D8%B3%D9%8A%D8%A9?authuser=0";
  const outOfScope = "أنا مساعد مكتبة النصر، وأجيب فقط عن معلومات المكتبة ومنتجاتها وأسعار القائمة وعنوانها وطرق التواصل. لا أملك معلومة مؤكدة عن هذا السؤال؛ تواصل معنا على 0954 672 747.";
  if (!question) return "اكتب سؤالك عن مكتبة النصر لأساعدك.";

  if (libraryQuestionHas(question, ["مرحبا", "اهلا", "السلام عليكم", "صباح الخير", "مساء الخير"])) {
    return "أهلاً وسهلاً بك في مكتبة النصر. اسألني عن المنتجات، الأسعار المدرجة، العنوان أو طرق التواصل.";
  }

  if (libraryQuestionHas(question, ["موقعنا الالكتروني", "موقعكم الالكتروني", "الموقع الالكتروني", "رابط الموقع", "رابط موقعكم", "رابط موقع المكتبه", "رابط موقع مكتبه النصر", "رابط صفحتكم", "google sites", "موقع مكتبه النصر الالكتروني", "الموقع الرسمي لمكتبه النصر", "صفحتكم الالكترونيه"])) {
    return `رابط الموقع الإلكتروني لمكتبة النصر: ${websiteLink}`;
  }

  const asksAboutLocation = libraryQuestionHas(question, ["عنوان المكتبه", "عنواننا", "عنوانكم", "اين المكتبه", "وين المكتبه", "اين مكتبه النصر", "وين مكتبه النصر", "اين مكتبتكم", "وين مكتبتكم", "اين مكانكم", "وين مكانكم", "خريطه المكتبه", "موقع المكتبه", "موقع مكتبه النصر", "موقعكم", "موقعنا على الخريطه", "hc93", "plus code", "شارع الجامعه العربيه", "كيف اصل للمكتبه", "كيف اصل لمكتبتكم"]);
  if (asksAboutLocation) {
    return `عنوان مكتبة النصر: شارع الجامعة العربية، دوما، ريف دمشق، سوريا. رمز الموقع على الخريطة HC93+22W. افتح الخرائط: ${mapsLink}`;
  }

  const asksAboutContact = libraryQuestionHas(question, ["رقم الهاتف", "رقمكم", "رقمنا", "رقم مكتبة النصر", "تواصل مع المكتبه", "كيف اتواصل معكم", "واتساب", "هاتف المكتبه", "اتصال بالمكتبه"]);
  if (asksAboutContact) {
    return "للتواصل مع مكتبة النصر اتصل على 0954 672 747، أو راسلنا عبر واتساب على الرقم نفسه.";
  }

  if (libraryQuestionHas(question, ["من نحن", "عن المكتبه", "عرفني عن المكتبه", "نبذه عن المكتبه", "معلومات عن المكتبه"])) {
    return "مكتبة النصر مكتبة في دوما، ريف دمشق، شارع الجامعة العربية. تشمل أقسامها القرطاسية والمكياج والهدايا والعطورات والألعاب والطباعة والمستلزمات المنزلية.";
  }

  const categoryAnswers = [
    { keys: ["قرطاسيه", "دفاتر", "اقلام", "ادوات مدرسيه"], name: "القرطاسية" },
    { keys: ["مكياج", "مكياج"], name: "المكياج" },
    { keys: ["هدايا", "هديه"], name: "الهدايا" },
    { keys: ["عطورات", "عطور", "عطر"], name: "العطورات" },
    { keys: ["العاب", "لعبه"], name: "الألعاب" },
    { keys: ["طباعه", "طباعه"], name: "الطباعة" },
    { keys: ["مستلزمات منزليه", "ادوات منزليه"], name: "المستلزمات المنزلية" }
  ];
  const matchedCategory = categoryAnswers.find(category => libraryQuestionHas(question, category.keys));
  const hasStoreCategoryContext = question.length <= 12 || libraryQuestionHas(question, ["عندكم", "عندكن", "لديكم", "تبيعون", "قسم", "منتجات", "متوفر", "اسعار", "سعر", "هل يوجد", "هل لديكم"]);
  if (matchedCategory && hasStoreCategoryContext) {
    if (["المكياج", "العطورات", "الألعاب"].includes(matchedCategory.name)) {
      return `نعم، ${matchedCategory.name} من أقسام مكتبة النصر. اسألنا عبر واتساب عن الأنواع المتوفرة والأسعار الحالية على 0954 672 747.`;
    }
    return `${matchedCategory.name} من أقسام مكتبة النصر. تصفّح المنتجات والأسعار المدرجة في قائمة الأسعار، وتواصل معنا للتأكد من التوفر والسعر الحالي.`;
  }

  if (libraryQuestionHas(question, ["اقسام المنتجات", "شو بتبيعوا", "ماذا تبيعون", "منتجاتكم", "اقسامكم", "المنتجات المتوفره", "شو عندكم"])) {
    return "تشمل أقسام مكتبة النصر: القرطاسية، المكياج، الهدايا، العطورات، الألعاب، الطباعة والمستلزمات المنزلية. بعض الأسعار والأصناف تُعرض في قائمة الأسعار، ويمكن الاستفسار عن المتوفر عبر واتساب.";
  }

  const wantsPrice = libraryQuestionHas(question, ["سعر", "اسعار", "ثمن", "بكم", "قديش", "تكلفه"]);
  if (wantsPrice) {
    const matchingProduct = products
      .filter(product => question.includes(normalizeLibraryQuestion(product.name)))
      .sort((a, b) => normalizeLibraryQuestion(b.name).length - normalizeLibraryQuestion(a.name).length)[0];
    if (matchingProduct) {
      return `السعر المدرج لـ${matchingProduct.name} هو ${matchingProduct.price} ل.س. الأسعار قد تتغير، لذلك يرجى التأكد من مكتبة النصر قبل الشراء.`;
    }
    if (!libraryQuestionHas(question, ["قائمه الاسعار", "اسعار المنتجات", "اسعاركم", "سعر المنتجات", "سعر دفتر", "سعر دفاتر", "سعر قلم", "سعر قرطاسيه", "سعر الهدايا", "اسعار مكتبه النصر"])) {
      return outOfScope;
    }
    return "تجد الأسعار المتاحة في قائمة الأسعار بالموقع. لم أجد صنفاً محدداً في سؤالك؛ اكتب اسم المنتج كما يظهر في القائمة أو تواصل معنا لمعرفة السعر والتوفر.";
  }

  if (libraryQuestionHas(question, ["فاتوره", "السله", "طلب", "شراء"])) {
    return "يمكنك إضافة المنتجات إلى سلة المشتريات ثم الضغط على «مشاركة الفاتورة عبر واتساب». ستُفتح رسالة جاهزة لمكتبة النصر لتراجعها وترسلها.";
  }

  return outOfScope;
}

const assistantToggle = document.getElementById("assistantToggle");
const assistantPanel = document.getElementById("assistantPanel");
const assistantClose = document.getElementById("assistantClose");
const assistantForm = document.getElementById("assistantForm");
const assistantInput = document.getElementById("assistantInput");
const assistantLog = document.getElementById("assistantLog");

function appendAssistantMessage(text, type) {
  const message = document.createElement("div");
  message.className = `assistant-message ${type === "user" ? "assistant-message-user" : "assistant-message-bot"}`;
  message.textContent = text;
  assistantLog.appendChild(message);
  assistantLog.scrollTop = assistantLog.scrollHeight;
}

function submitAssistantQuestion(text) {
  const question = String(text || "").trim().slice(0, 240);
  if (!question) return;
  appendAssistantMessage(question, "user");
  appendAssistantMessage(answerLibraryQuestion(question), "bot");
}

assistantToggle.addEventListener("click", () => {
  const willOpen = assistantPanel.hidden;
  assistantPanel.hidden = !willOpen;
  assistantToggle.setAttribute("aria-expanded", String(willOpen));
  if (willOpen) assistantInput.focus();
});
assistantClose.addEventListener("click", () => {
  assistantPanel.hidden = true;
  assistantToggle.setAttribute("aria-expanded", "false");
  assistantToggle.focus();
});
assistantForm.addEventListener("submit", event => {
  event.preventDefault();
  submitAssistantQuestion(assistantInput.value);
  assistantInput.value = "";
  assistantInput.focus();
});
document.querySelectorAll("[data-assistant-question]").forEach(button => {
  button.addEventListener("click", () => submitAssistantQuestion(button.dataset.assistantQuestion));
});

// ===== تشغيل أولي =====
renderTabs();
renderCatalog();
updateCartUI();
