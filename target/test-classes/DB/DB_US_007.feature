@end2end
Feature: "support_tickets" tablosunda  "ticket" değeri dogrulama

  Scenario:Database içindeki "support_tickets" tablosunda  "ticket" değeri  4 ile başlayan dataların "subject" bilgisini doğrulayınız.
    * Database baglantısı kurulur
    * Support tickets query hazirlanir
    * Support tickets data dogrulanir
    * Database kapatilir