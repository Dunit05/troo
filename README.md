![Logo](https://filose-mirror.000webhostapp.com/furot.png)

# Troo by Furot

Here at Troo, we aim to make food delivery as easy as a couple of clicks on your screen from the comfort of your home.

Troo is a Greek food delivery app, serving Toronto and the GTA.
Troo specializes in bringing Greek cuisine right to your doorstep, so you can focus on the things that matter most to you, and less on the preparation of your meals.
Troo delivers to residential homes, as well as offices, schools, colleges and universities.
The software will allow the user to order food from a certain restaurant listed in the app, they will be able to make the purchase and be able to track how long it will take for the food to be made and the time it will eventually take to get delivered to their desired address.
Our app will be able to process payments and issue the user a transactional receipt.

## Authors

- [Tommy D.](https://www.tdmwebsolutions.com/team/tommy)
- [Andrew Evans](https://www.furot.tech)
- [Suchir Ladda](https://www.furot.tech)

## Features

- Login and Sign up (Authentication)

## API Reference

#### Send Email

```http
  POST SENDGRID API
```

| Parameter          | Type     | Description                |
| :----------------- | :------- | :------------------------- |
| `SENDGRID_API_KEY` | `string` | **Required**. Your API key |
| `To`               | `string` | **Required**. To email     |
| `From`             | `string` | **Required**. From Email   |
| `Subject`          | `string` | Subject                    |
| `Content`          | `string` | Email Content              |

#### Get item

```http
  POST https://maps.googleapis.com/maps/api/place/autocomplete/json
```

| Parameter             | Type     | Description                |
| :-------------------- | :------- | :------------------------- |
| `GOOGLE_MAPS_API_KEY` | `string` | **Required**. Your API key |
| `Text Address`        | `string` | **Required**. Text Address |
| `UUID`                | `string` | **Required**. Random UUID  |

## Run Locally

Clone the project

```
  git clone https://github.com/Dunit05/troo.git
```

## Files (Data)

- User data is stored in src/main/resources/com/troo/data/users.txt
- Password are hashed for maximum security
- User data is stored as follows:

```
[Email:
Password:
First Name:
Last Name:
Phone:
Address:]
```

- Transaction data is store in src/main/resources/com/troo/data/transactions.txt
- Transaction data from successful transactions are stored as follows

```
[Account:
Transaction:
Receipt:]
```

## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`GOOGLE_MAPS_API_KEY`

`SENDGRID_API_KEY`

`FROM_EMAIL`
