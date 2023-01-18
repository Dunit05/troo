![Logo](https://filose-mirror.000webhostapp.com/furot.png)

# tróo by Furot

Here at tróo, we aim to make food delivery as easy as a couple of clicks on your screen from the comfort of your home.

tróo is a Greek food delivery app, serving Toronto and the GTA.
tróo specializes in bringing Greek cuisine right to your doorstep, so you can focus on the things that matter most to you, and less on the preparation of your meals.
tróo delivers to residential homes, as well as offices, schools, colleges and universities.
The software will allow the user to order food from a certain restaurant listed in the app, they will be able to make the purchase and be able to track how long it will take for the food to be made and the time it will eventually take to get delivered to their desired address.
Our app will be able to process payments and issue the user a transactional receipt.

## Authors

- [Tommy D.](https://www.tdmwebsolutions.com/team/tommy)
- [Andrew Evans](https://www.furot.tech)
- [Suchir Ladda](https://www.furot.tech)

## Features

- GUI - User Interface.
- Login and Sign up (Authentication).
- Home page to list all the restaurants that you could order form.
- Dynamic restaurant order page (List the dishes associated with each restaurant), allow the user to add restaurants to their cart.
- Checkout (Cart) / POS System to allow the user to checkout and buy the items that they added to their cart.
- Receipt Generator to generate a receipt after the payment from the user goes through. Stores transaction data in a text file.
- Email functionality to send the user the receipt, and a welcome email upon sign up.
- Dark mode functionality.
- Live google maps data to calculate total delivery time.

## Sample User

If you are testing the app, you can use our sample user that is already registered

- dev@furot.tech
- FurotTechAdmin123!

If you would like the receipt to be sent to your email, change the email associated with the default account, in users.txt

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

#### Get autocomplete address predictions

```http
  POST https://maps.googleapis.com/maps/api/place/autocomplete/json
```

| Parameter             | Type     | Description                |
| :-------------------- | :------- | :------------------------- |
| `GOOGLE_MAPS_API_KEY` | `string` | **Required**. Your API key |
| `Text Address`        | `string` | **Required**. Text Address |
| `UUID`                | `string` | **Required**. Random UUID  |

#### Get live distance/traffic/travel time from point A to point B

```http
  POST https://maps.googleapis.com/maps/api/distancematrix/json
```

| Parameter             | Type     | Description                     |
| :-------------------- | :------- | :------------------------------ |
| `GOOGLE_MAPS_API_KEY` | `string` | **Required**. Your API key      |
| `Text From Address`   | `string` | **Required**. Text From Address |
| `Test To Address`     | `string` | **Required**. Text To Address   |

## Run Locally

Clone the project

```
  git clone https://github.com/Dunit05/troo.git
```

## Files (Data)

- User data is stored in src/main/resources/com/troo/data/user_data/users.txt
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

- Transaction data is stored in src/main/resources/com/troo/data/transaction_data/transactions.txt
- Transaction data from successful transactions are stored as follows

```
[Account:
Transaction:
Receipt:]
```

- Menu items are stored in src/main/resources/com/troo/data/restaurants/
- Each restaurant has its own file contaning the menu items
- Each menu item has a path to an image, the program posts to a URL with that image path to get the image

## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`GOOGLE_MAPS_API_KEY`

`SENDGRID_API_KEY`

`FROM_EMAIL`
