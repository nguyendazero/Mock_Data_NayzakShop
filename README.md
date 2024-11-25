# HAIBAZO ITS RCT API Mock

This service provides a minimal setup to run the ITS RCT API locally and allows full customization of the API response.

## How to start

You can start this service using Java (21) + Maven, or Docker.

### Docker

With Docker installed, run:

```bash
docker compose up --build
```

then go to [http://localhost:2180/its-rct/v1/greetings](http://localhost:2180/its-rct/v1/greetings) (with `2180` as the `ports` in `docker-compose.yml`). The following should appear in your browser:

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

Take your eye into `./api-mock/api-mock-setting.csv` and `./api-mock/its-rct`, and ignore others for now.

### api-mock-setting.csv

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

### Add setting to `api-mock-setting.csv`

Add the following to your setting file:

```bash
/its-rct/v1/products,./its-rct/products.json,UTF-8,GET,200,
/its-rct/v1/products/{product_id},./its-rct/products_0.json,UTF-8,GET,200,
/its-rct/v1/products,./its-rct/products.json,UTF-8,POST,200,
/its-rct/v1/products/{product_id},./its-rct/products_0.json,UTF-8,DELETE,200,
```
