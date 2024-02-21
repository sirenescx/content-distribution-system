# Content Distribution System as a Component of the Development of Social Mechanics in a Financial Application (Item Keeper Service)

## Problem Statement
Development of a content distribution system.

## Author
Maria Manakhova

## Supervisors
Arakcheev Anton, Danzan Shurkaev

## Work Plan
|                                                                                                  | Deadline            |
|--------------------------------------------------------------------------------------------------|---------------------|
| System architecture development. Database schema design. Technology stack selection.             | 28th December, 2023 |
| Development of a service for automated data uploading: data fetching and parsing, cronjob setup. | 21st February, 2024 |
| Paragraph                                                                                        | 21st February, 2024 |


## Description of Used Data
Used data is stored in RSS (Rich Site Summary) format, which is a subtype of XML format. RSS data is provided by most news websites, for example, RBC (http://static.feed.rbc.ru/rbc/logical/footer/news.rss).

The common data that is provided in RSS files:
1. Information about the news source
2. A list of current news, containing following information:
   * news headline (title)
   * link to the news (link)
   * date of publication of the news (pubDate)
   * news description (description)
   * news category (category)
   * guid

Data is updated depending on the source.

## Final Product Description
The final product is a system of two web services:
### 1. Item Keeper service
This service is required for parsing data from rss files of news sources by cron job.

**Technology Stack:** 
* Programming Languages: Kotlin, GraphQL
* Frameworks: Spring, gRPC, Quartz
* Database: PostgreSQL
* Other: Rome, Spring Data JPA, JSON Processing API

### 2. Item Keeper Admin service
**Technology Stack:**
* Programming Languages: Kotlin, GraphQL
* Frameworks: Spring, gRPC
* Other: Swagger