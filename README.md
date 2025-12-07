# FoodProject Microservice

FoodProject, yemek sipariş akışını mikroservis mimarisi ile yöneten bir örnek projedir. Amaç; kullanıcı, restoran, sipariş ve kargo süreçlerini bağımsız servisler aracılığıyla yönetmek, ölçeklenebilir ve esnek bir yapı oluşturmaktır.

---

## 🚀 Mimari Genel Bakış

Proje aşağıdaki mikroservislerden oluşur:

* **User Service** – Kullanıcı kayıt, kimlik doğrulama ve kullanıcı yönetimi
* **Restaurant Service** – Restoran bilgileri, menü yönetimi
* **Order Service** – Sipariş oluşturma, sipariş durumu ve sipariş yönetimi
* **Shipping Service** – Kargo / teslimat bilgileri
* **Eureka Server** – Servis keşfi
* **API Gateway** – Servislerin tek bir giriş noktası üzerinden yönetilmesi

Her servis kendi bağımsız veri tabanı ve yapılandırması ile çalışır.

---

## 📦 Teknolojiler

* **Java / Spring Boot**
* **Spring Cloud** (Eureka, Gateway vb.)
* **Docker & Docker Compose**
* **Microservice Architecture**
* **Redis & RabbitMQ**
---

## 🛠️ Kurulum

Aşağıdaki komut ile tüm servisleri birlikte ayağa kaldırabilirsiniz:

```bash
docker-compose up --build
```

> Servislerin ilk ayağa kalkışı birkaç saniye sürebilir.

---

## 🌐 Servis URL'leri

| Servis             | URL                                            |
| ------------------ | ---------------------------------------------- |
| API Gateway        | [http://localhost:8080](http://localhost:8080) |
| Eureka Server      | [http://localhost:8761](http://localhost:8761) |
| User Service       | Gateway üzerinden erişilir                     |
| Restaurant Service | Gateway üzerinden erişilir                     |
| Order Service      | Gateway üzerinden erişilir                     |
| Shipping Service   | Gateway üzerinden erişilir                     |

---

## 📚 Dosya Yapısı

```
foodProject-microservice/
   ├── eurekaServer/
   ├── gateway/
   ├── users-service/
   ├── restaurant-service/
   ├── order-service/
   ├── shipping-service/
   ├── docker-compose.yml
   └── README.md
```

---

## 🧪 Test & Geliştirme

* Her servisin bağımsız çalışabilir olması sayesinde geliştirme süreci kolaydır.
* API testleri için Postman veya benzeri bir araç önerilir.
* Üretim ortamı için servislerin Kubernetes üzerinde çalıştırılması önerilebilir.

---

## 🤝 Katkıda Bulunma

Katkıda bulunmak isterseniz pull request açabilir veya issue oluşturabilirsiniz.

---

## 📄 Lisans

Bu proje **MIT Lisansı** ile dağıtılmaktadır.

---

