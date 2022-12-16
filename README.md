![Logo](https://filose-mirror.000webhostapp.com/furot.png)

# Troo by Furot

Here at Troo, we aim to make food delivery as easy as a couple of clicks on your screen from the comfort of your home.

Troo is a Greek food delivery app, serving Toronto and the GTA.
Troo specializes in bringing Greek cuisine right to your doorstep, so you can focus on the things that matter most to you, and less on the preparation of your meals.
Troo delivers to residential homes, as well as offices, schools, colleges and universities.
The software will allow the user to order food from a certain restaurant listed in the app, they will be able to make the purchase and be able to track how long it will take for the food to be made and the time it will eventually take to get delivered to their desired address.
Our app will be able to process payments and issue the user a transactional receipt.

## Authors

- [Tommy D.](https://www.furot.tech)
- [Andrew Evans](https://www.furot.tech)
- [Suchir Ladda](https://www.furot.tech)

## Features

- Login or Sign up (Authentication)

## API Reference

#### Get all items

```http
  GET /api/items
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### Get item

```http
  GET /api/items/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### add(num1, num2)

Takes two numbers and returns the sum.

## Run Locally

Clone the project

```
  git clone https://link-to-project
```

## Files (Data)

- Data is stored int src/main/resources/com/troo/data/FileName.txt
- Password are hashed for maximum security
- User data is stored as follows: email,password,userId,firstName,lastName,phone,address
