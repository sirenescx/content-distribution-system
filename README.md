![graphql](https://img.shields.io/badge/GraphQl-E10098?style=for-the-badge&logo=graphql&logoColor=white) ![kotlin](https://img.shields.io/badge/kotlin-ff7a00?style=for-the-badge&logo=kotlin) ![spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white) ![nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white) ![gradle](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white) ![postgres](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white) ![kubernetes](https://img.shields.io/badge/kubernetes-326ce5.svg?&style=for-the-badge&logo=kubernetes&logoColor=white) ![docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white) ![grafana](https://img.shields.io/badge/Grafana-F2F4F9?style=for-the-badge&logo=grafana&logoColor=orange&labelColor=F2F4F) ![prometheus](https://img.shields.io/badge/Prometheus-000000?style=for-the-badge&logo=prometheus&labelColor=000000)

# Content Distribution System as a Component of the Development of Social Mechanics in a Financial Application

## Problem Statement
The main objective of this project is to develop a platform for managing news compilations or feeds that allows to retrieve articles from various data sources and store them for later distribution. The developed backend system serves as a centralized hub where publishers, content creators and consumers can access, organize and efficiently distribute news content.

## Description of Used Data
Used data is stored in RSS (Rich Site Summary) format, which is a subtype of XML format. RSS data is provided by most news websites, for example, [RBC](http://static.feed.rbc.ru/rbc/logical/footer/news.rss).

The common data that is provided in RSS files:
1. Information about the news source (name of the news source, logo picture, language)
2. A list of latest news articles, containing following information:
   * news headline (title)
   * link to the news (link)
   * date of publication of the news (pubDate)
   * news description (description)
   * news category (category)
   * guid

Depending on the source, RSS files could also contain:
* full text of the news article
* identifier of the news article
* tags related to key details of the news article
* and much more

The frequency of data updates depends on the source.

Data parsing is implemented via usage of ROME framework for RSS and Atom feeds, JSON Pointer and mapping configuration file in JSON format.

## Final Product Description
The final product is a backend system consisting of two web services:
![Scheme](images/architecture-scheme.png)
### 1. Item Keeper service
![Scheme](images/item-keeper-architecture.png)
Item Keeper is a service that is required for parsing data from RSS files of news sources by a cron job and storing parsed data in PostgreSQL database.

**Technology Stack:**
* Programming Languages: Kotlin, GraphQL
* Frameworks: Spring, Quartz
* Database: PostgreSQL
* Other: Spring Data JPA, JSON Processing API, Gradle

### 2. Item Keeper Admin service
This service is required for creating selection of news articles from the data provided by Item Keeper service.
![Scheme](images/item-keeper-admin-architecture.png)
**Technology Stack:**
* Programming Languages: Kotlin, GraphQL
* Other: Swagger, Gradle

### Database scheme
![Scheme](images/database-scheme.png)

### Monitoring
System monitoring is implemented using pgexporter for database metrics, Spring Boot Actuator for web services metrics, Prometheus for scraping and Grafana for visualisation
![Scheme](images/db-grafana.png)
![Scheme](images/service-grafana.png)

### Deployment 
![Scheme](images/deployment.png)
