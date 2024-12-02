# HAIBAZO ITS RCT API Mock

This service provides a minimal setup to run the ITS RCT API locally and allows full customization of the API response.

## How to start

You can start this service using Java (21) + Maven, or Docker.

### Environment and configuration

#### Environment

Duplicate the `./.env.example` file to `./.env.developement` and fill in (or change) the following:

```bash
HAIBAZO_BFF_MOCK_STORAGE = "local"
HAIBAZO_BFF_MOCK_BASE_FOLDER_PATH = "./haibazo-bff-mock-static"
```

Others can be left as default.

#### Configuration

Duplicate the `./haibazo-bff-mock-webapi/src/main/resources/application.properties.example` file to `./haibazo-bff-mock-webapi/src/main/resources/application.properties` and fill in the necessary information. (leave it default if you don't know what to do)

### Docker

With Docker installed, run:

```bash
docker compose up haibazo-bff-mock-webapi-development
```

or, you can build the image first and then run it:

```bash
docker compose build haibazo-bff-mock-webapi-development
docker compose up haibazo-bff-mock-webapi-development
```

then go to [http://localhost:2380/its-rct/v1/greetings](http://localhost:2380/its-rct/v1/greetings) (with `2380` as the `ports` in `docker-compose.yml`). The following should appear in your browser:

```json
{
  "status": "NOT_FOUND",
  "data": null,
  "message": "NO_MOCK_SETTING_FOUND",
  "code": null,
  "meta": null,
  "error": null
}
```

### Java (21) + Maven

Comming soon. (Zoooooo)

## How to use

Take your eye into `./haibazo-bff-mock-static/bff-mock-setting.csv` file and `./haibazo-bff-mock-static/its-rct` folder, and ignore others for now.

### bff-mock-setting.csv

A `csv` file contains settings to route all endpoints to the corresponding `.json` files, with:

| Column  | Description                                                                                                           | Changable |
| ------- | --------------------------------------------------------------------------------------------------------------------- | :-------: |
| URI     | The API absolute endpoint started with `/its-rct/`, represents the real API endpoint                                  |   false   |
| File    | The corresponding `.json` file responded by `URI`                                                                     |   true    |
| Charset | -                                                                                                                     |   false   |
| Method  | The corresponding `HttpMethod` for the URI. The couple `URI` and `Method` will determine which file should be handled |   true    |
| Status  | The `HttpStatus` that you want to be returned to the client.                                                          |   true    |

### `its-rct` folder

A place to look for the `.json` file when the API endpoint starts with `its-rct`.

## Example

To mocks the following APIs:

```bash
[GET] /its-rct/v1/products
[GET] /its-rct/v1/products/1234567890
[POST] /its-rct/v1/products
[DELETE] /its-rct/v1/products/1234567890
```

### Add setting to `bff-mock-setting.csv`

Add the following to your setting file:

```bash
/its-rct/v1/products,./its-rct/products.json,UTF-8,GET,200,
/its-rct/v1/products/{product_id},./its-rct/products_0.json,UTF-8,GET,200,
/its-rct/v1/products,./its-rct/products.json,UTF-8,POST,200,
/its-rct/v1/products/{product_id},./its-rct/products_0.json,UTF-8,DELETE,200,
```
