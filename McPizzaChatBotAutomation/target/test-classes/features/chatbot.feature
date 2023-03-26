Feature: Order Pizza from chat bot

Scenario Outline: Verify if user is able order Pizza using Chat bot
		Given user had open chat bot window
		When user ordered "<type>" "<crust>" "<size>" pizza details 
		Then chatbot is able to place order
		And user is able to provide feedback
	
Examples:
| type    | topping      | crust       | size  |
| veg     | cheese,tomato | thick crust | small |
| non veg | pepperoni,bacon | thick crust | medium |