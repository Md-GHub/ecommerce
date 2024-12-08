📜 E-Commerce API Documentation

Base URL
http://localhost:8080/api

📁 Endpoints
📚 Category
1. Get All Categories
URL: /api/public/categories
Method: GET
Description: Retrieve a list of all categories available in the system.


2. Create a New Category
URL: /api/admin/categories
Method: POST
Description: Create a new category.
Request Body: JSON



3. Update Category by ID
URL: /api/admin/categories/{id}
Method: PUT
Path Parameters:
id – Category ID
Description: Update an existing category by its ID.
Request Body: JSON



4. Delete Category
URL: /api/admin/categories/{id}
Method: DELETE
Path Parameters:
id – Category ID
Description: Delete a category by its ID.


🛒 Product
1. Add Product to Category
URL: /api/admin/categories/{categoryId}/product
Method: POST
Path Parameters:
categoryId – Category ID
Request Body: JSON
json



Here's how you can structure your API documentation on GitHub:

📜 E-Commerce API Documentation
Base URL
http://localhost:8080/api

📁 Endpoints
📚 Category
1. Get All Categories
URL: /api/public/categories
Method: GET
Description: Retrieve a list of all categories available in the system.
2. Create a New Category
URL: /api/admin/categories
Method: POST
Description: Create a new category.
Request Body: JSON
json
Copy code
{
  "name": "Category Name",
  "description": "Category Description"
}
3. Update Category by ID
URL: /api/admin/categories/{id}
Method: PUT
Path Parameters:
id – Category ID
Description: Update an existing category by its ID.
Request Body: JSON
json
Copy code
{
  "name": "Updated Category Name",
  "description": "Updated Description"
}
4. Delete Category
URL: /api/admin/categories/{id}
Method: DELETE
Path Parameters:
id – Category ID
Description: Delete a category by its ID.


🛒 Product
1. Add Product to Category
URL: /api/admin/categories/{categoryId}/product
Method: POST
Path Parameters:
categoryId – Category ID
Request Body: JSON
json


2. Get All Products
URL: /api/public/products
Method: GET
Description: Get a list of all products available in the system.


3. Get Products by Category
URL: /api/public/categories/{categoryId}/products
Method: GET
Path Parameters:
categoryId – Category ID
Description: Retrieve products in a specific category.


4. Get Product by Keyword
URL: /api/public/product/keyword/{keyword}
Method: GET
Path Parameters:
keyword – Keyword to search for products
Description: Search products based on a keyword.


5. Update Product Details
URL: /api/products/{productId}
Method: PUT
Path Parameters:
productId – Product ID
Request Body: JSON


6. Delete Product
URL: /api/admin/products/{productId}
Method: DELETE
Path Parameters:
productId – Product ID
Description: Remove a product from the system.


🔐 Security
1. Login
URL: /api/auth/signin
Method: POST
Request Body: JSON


2. Register
URL: /api/auth/signup
Method: POST
Request Body: JSON


3. Get Username
URL: /api/auth/username
Method: GET
Description: Retrieve the currently authenticated user's username.


4. Logout
URL: /api/auth/signout
Method: POST
Description: Log out the currently authenticated user.


🛒 Cart
1. Add Product to Cart
URL: /api/carts/products/{userId}/quantity/{quantity}
Method: POST
Path Parameters:
userId – ID of the user
quantity – Number of items
Description: Add a product to a user's cart.


2. Get User's Cart
URL: /api/carts/users/cart
Method: GET
Description: Retrieve the current cart contents for a user.


3. Delete Product from Cart
URL: /api/carts/{cartId}/product/{productId}
Method: DELETE
Path Parameters:
cartId – Cart ID
productId – ID of the product to delete
Description: Remove a product from the cart.


4. Update Product Quantity in Cart
URL: /api/cart/products/{userId}/quantity/{quantity}
Method: PUT
Description: Update the quantity of a product in the user's cart.


🗺️ Address
1. Get Address by ID
URL: /api/address/{id}
Method: GET
Path Parameters:
id – Address ID
Description: Get address details by ID.


2. Create a New User Address
URL: /api/address
Method: POST
Request Body: JSON
json


3. Get All User Addresses
URL: /api/address
Method: GET
Description: Get all addresses for a user.

4. Update Address
URL: /api/address/{id}
Method: PUT
Path Parameters:
id – Address ID
Request Body: JSON



📦 Order
Place an Order
URL: /api/order/users/payments/{method}
Method: POST
Path Parameters:
method – Payment method (CARD, PAYPAL, etc.)
Description: Place an order with a specified payment method.

