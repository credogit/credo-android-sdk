#Credo-Payment
#Credo-Payment
#Credo-Payment
#csp
#credo-android-sdk
# credo-android-sdk

## Credo SDK Android

This library allows for the easy integration of [Credo] into your Android application. It shoulders the burden of PCI compliance by sending credit card details directly to our servers rather than to your server.

## Flow Summary

1. Collect user's card details, email and phone number. 
	
2. Initialize the transaction by creating an object of the Transaction class with two arguments passed to the constructor.
	- The first argument is the public key of the merchant
	- The second argument is the secret key of the merchant.
	- Both public and secret keys are provided by Credo to the merchants.
	- App prompts backend to initialize transactions.
	- Your backend returns a `payment slug` which is returned when `Initiate Payment` endpoint is called
	- App provides the `payment slug` and card details to our SDK's using the `thirdPartyPay` and `verifyCardNumber` methods
	
3. Once request is successful, an event is sent to the `onSuccess` callback.

## Requirements
- minSdkVersion 21
- targetSdkVersion 30
- sourceCompatibility JavaVersion.VERSION_1_8
- targetCompatibility JavaVersion.VERSION_1_8

## Installation
- To start using this library, simply add the following to project build.gradle

```
allprojects{
	  repositories{
		  ...
		  maven {url 'https://jitpack.io' }
	  }
  }
```
- Then add the following to your module build.gradle file

```
dependencies{
	...
	implementation 'com.github.Joealtidore1:csp:1.2'
  }
```

## Usage

### 1. Permissions
To use this library, your app must declare internet permission. Add the following code to the application level of your AndroidManifest.xml.

```xml
	<uses-permission android:name="android.permission.INTERNET" />
```

### 2. Initializing SDK
	To use [Credo] SDK, you need to first initialize it by using the `Transaction` class.
	
```java
public class App extends AppCompatActivity{
	...
	Transaction transaction;
	...
	
	@Override
	protected void onCreate(...){
		super.onCreate();
		try{
			transaction = new Transaction(PUBLIC_KEY, SECRET_KEY);
		}catch(NoSuchAlgorithmException | KeyManagementException|KeystoreException e){
			Log.d("Initialization Error", e.getMessage());
		}
	}
}
```
Ensure to perform this instantiation in the `onCreate` method of your Activity.

### 3. Initiate Payment
Payment transaction can be initiated with the `initiatePayment` method: 
## Parameters
```
amount``` The amount to be transacted.
`currency` The currency to be transacted in. 
`redirectUrl` The url to redirect to after transaction.
`transRef` The transaction reference for the payment
`paymentOptions` option can be "CARD" or "USSD" or "BANK"
`customerName` Name of the customer
`customerEmail` Email address of the customer
`customerPhone` Phone number of the customer
`customCallback` an implementation of the `InitiatePaymentCallback` Interface. If the method call is success, the `paymentSlug` is returned into the `onSuccess` method of the `InitiatePaymentCall`
	
```java
	.
	.
	.
	if(transaction != null){
		transaction.initiatePayment(5000.00, "NGN", https://www.example.com", "1234567890", "CARD", "Andre Chloe", "me@you.com", +234 700 00090000", new Transaction.InitiatePaymentCallBack(){
			@Override
			public void onSucces(InitiateSchema schema){
				//get payment slug using schema object.
				if(schema != null){
					String paymentSlug + schema.getPaymentSlug();
				}
				/*
				 *
				 *Your code here
				 *
				 */
			}
			
			@Override
			public void onFailure(Throwable t){
				//Request failed
				//Add your implementations here
			}
		});
	}
```

### 4. Verify Card Details
	
After a successful `initiatePayment` request, you have to verify the card details using the payment slug generated in step 3 above using the `verifyCardNumber` method.
## Parameters
`cardNumber` the number of the debit card	
`orderCurrency` The currency to be transacted in.
`paymentSlug` this is part of the response of `initiatePayment` method in step 3 above
`verifyCardCallBack` a callback method for getting api call response
	
```java
	.
	.
	.
	
	if(transaction != null && paymentSlug != null && !paymentSlug.isEmpty()){
		transactions.verifyCardNumber("76845699064322", "NGN", paymentSlug, new Transactions.VerifyCardCallBack() {
                @Override
                public void onSuccess(VerifyCardSchema schema) {
                    if(schema != null && schema.getGatewayRecommendation() != null &&
                    schema.getGatewayRecommendation().equalsIgnoreCase("PROCEED")){
                        //Perform payment here
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("Verification Error", t.getMessage());
                }
            });
	}
```
	
After a successful verification, payment can then be made using the


### 5. Make Payment

After the card details have been successfully confirmed, payment is made using the `thirdPartyPay` method
	
## Parameters
`amount` The amount to be transacted.   
`ordeCurrency` The currency to be transacted in. 
`cardNumber` The number of the verified debit card.
`expiryMonth` The expiry month of the verified card.
`expiryYear` The expiry Year of the verified card.
`securityCode` The cvv number of the verified card.
`transRef` The transaction reference used to during initiate payment stage.
`customerEmail` Email address of the customer.
`customerName` Name of the customer.
`customerPhone` Phone number of the customer.
`paymentSlug` this is part of the response of `initiatePayment` method in step 3 above.
`thirdPartyCallBack` an implementation of the `ThirdPartyCallback` Interface for getting api call response.

```java
	...
		if(transaction != null && paymentSlug != null && !paymentSlug.isEmpty(){
			transactions.thirdPartyPay(5000.00, "NGN", "76845699064322", "04", "2022", "123", "1234567890", "me@you.com", "Andre Chloe", "+234 700 00090000", paymentSlug, new Transactions.ThirdPartyCallBack() {
			    @Override
			    public void onSuccess(ThirdPartySchema schema) {
				if(schema != null && schema.getStatus() != null && schema.getStatus().equalsIgnoreCase("success")){
				    //Transaction was successful

				}
			    }

			    @Override
			    public void onFailure(Throwable t) {
				Log.d("PAYMENT FAILURE", t.getMessage());
			    }
			});
		}
```
	
you can check out the example on [this link](https://github.com/nugitech/credo-android-sdk/tree/main/ExampleApp)
	






	
