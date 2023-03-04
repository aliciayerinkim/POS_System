from fileinput import close
import random
import csv
import datetime

cups = 0 #includes cup, lid, and straw
boxes = 0 #for plate and bigger plate
bowls = 0 #for all add-ons and bowl meals
napkins = 0 #for every meal
silverware = 0 #for every meal
bag = 0 #for every meal
mealOrderId = 1
inventoryId = 1


"""
Opens csv file inventory and parses for the quantity value for the given inventoryOrderID, de-increments the quantity value by 1.
@param int, menuItemID
@return void
"""
def deIncrement(inventoryItemId):
    file =  open('inventory.csv', 'r')
    content = file.read().splitlines(True)
    line = content[inventoryItemId].split(',')
    line[-1] = int(line[-1]) - 1
    content[inventoryItemId] = ",".join(str(v) for v in line) + '\r'


    file =  open('inventory.csv', 'w')
    for i in content:
      file.write(i)
    file.close()

"""
Creates an order to be appended to order.csv. Has function calls to createMeal and createAddOn.
@param int, orderId
@param date, date
@return float
"""
def createOrder(orderId, date):
    #Order
    numMeals = random.randint(0,3)
    numAddOns = random.randint(0,2)

    employeeID = random.randint(1,7)
    orderTotal = 0

    global mealOrderId

    # Order cannot be empty
    while(numMeals == 0  and numAddOns == 0) :
        numMeals = random.randint(0,3) # TODO - changed these two values, look at when the day ends 
        numAddOns = random.randint(0,2)

    for _ in range(numMeals+1):
        mealType = random.randint(1,3)
        orderTotal += createMeal(mealType, mealOrderId, orderId)
        mealOrderId += 1
    for _ in range(numAddOns+1):
        orderTotal += createAddOn(orderId)
    
    orderData = [orderId, date, employeeID, round(orderTotal, 2)]
    with open('Order.csv', 'a', encoding='UTF8', newline='') as o:
        writer = csv.writer(o)
        writer.writerow(orderData)
    o.close()
    return orderTotal #this is not including tax

"""
Creates a meal to be added to an order, as well as .csv files that handle meal IDs and order IDs. Given meal type, will return a different price value that reflects the corresponding price in the meal_price table in the database.
@param int, mealType
@param int, mealID
@param int, orderID
@return int 
"""
def createMeal(mealType, mealID, orderID):
    omb = open('order_meals_bridge.csv', 'a', newline='')
    m = open('meal.csv', 'a', newline='')
    mib = open('meal_item_bridge.csv', 'a', newline='')

    global boxes
    global bowls
  
    if mealType == 1: #bowl
        menuIndex = random.randrange(1, 6)
        meal = [mealID, 1, menuIndex]
        writer = csv.writer(m)
        writer.writerow(meal)
        m.close()
        deIncrement(menuIndex)

        orderMeals = [orderID, mealID]
        writer = csv.writer(omb)
        writer.writerow(orderMeals)
        omb.close()

        menuIndex = random.randrange(6, 19)
        mealsItems = [mealID, menuIndex]
        writer = csv.writer(mib)
        writer.writerow(mealsItems)
        mib.close()
        deIncrement(menuIndex)

        bowls += 1

        return 8.75 # bowl price without tax

    if mealType == 2: #plate
        menuIndex = random.randrange(1, 6)
        meal = [mealID, 2, menuIndex]
        writer = csv.writer(m)
        writer.writerow(meal)
        m.close()
        deIncrement(menuIndex)

        orderMeals = [orderID, mealID]
        writer = csv.writer(omb)
        writer.writerow(orderMeals)
        omb.close()

        for i in range(2):
            menuIndex = random.randrange(6, 19)
            mealsItems = [mealID, menuIndex]
            writer = csv.writer(mib)
            writer.writerow(mealsItems)
            deIncrement(menuIndex)

        mib.close()

        boxes += 1

        return 9.00 # plate price without tax

    if mealType == 3: #bigger plate
        menuIndex = random.randrange(1, 6)
        meal = [mealID, 3, menuIndex]
        writer = csv.writer(m)
        writer.writerow(meal)
        m.close() 
        deIncrement(menuIndex)

        orderMeals = [orderID, mealID]
        writer = csv.writer(omb)
        writer.writerow(orderMeals)
        omb.close()

        for i in range(3):
            menuIndex = random.randrange(6, 19)
            mealsItems = [mealID, menuIndex]
            writer = csv.writer(mib)
            writer.writerow(mealsItems)
            deIncrement(menuIndex)

        mib.close()

        boxes += 1

        return 10.50 # bigger plate price without tax

"""
Randomly selects a menu item to be created as an add on. It then writes the new add on ID along with the orderID passed in to the Order_addOn_Items.csv. createAddOn will also increment the amount of miscItems used for the specific random add on. 
@param int, orderID
@return float
"""
def createAddOn(orderID):
    global cups
    global bowls
    global napkins
    global silverware
    global bag
    
    menuID = random.randrange(1, 21)

    if menuID == 19:
        cups += 1
    elif menuID != 20:
        bowls += 1
        napkins += 1
        silverware += 1
        bag += 1

    data = [orderID, menuID]

    with open('Order_addOn_Items.csv', 'a', encoding='UTF8', newline='') as f:
        writer = csv.writer(f)
        writer.writerow(data)
    f.close()
    
    file =  open('menu_item.csv')
    content = file.readlines()
    line = content[menuID].split(',')
    file.close()
    deIncrement(menuID)
    return float(line[2])

"""
Increments the minute by a random number and returns a newly updated date value. 
@param date, currentDatetime
@return date
"""
def incrementDateTime(currentDatetime):
    incrementRange = random.randint(1,10)
    currentDatetime = currentDatetime + datetime.timedelta(minutes = incrementRange)
    return currentDatetime

"""
Update how many miscellaneous items there are and write the item ID, name, unit purchase price, and sales prices into the file.
@return void
"""
def updateMiscItems():   # TODO - change how misc items is updating to the file (should now be inventory)
    #this function may not be needed anymore at all because of updating by ingredient now

    header = ['Misc_item_ID', 'Misc_Item_name', 'Unit_purchase_price', 'Quantity']
    cupData = [1, 'Cups', 0.01, cups]
    boxData = [2, 'Boxes', 0.03, boxes]
    bowlData = [3, 'Bowls', 0.02, bowls]
    napkinData = [4, 'Napkins', 0.01, napkins]
    silverwareData = [5, 'Silverware', 0.01, silverware]
    bagData = [6, 'Bags', 0.01, bag]

    file = open('Misc_items.csv', 'w')
    file.truncate(0)
    
    writer = csv.writer(file)

    writer.writerow(header)
    writer.writerow(cupData)
    writer.writerow(boxData)
    writer.writerow(bowlData)
    writer.writerow(silverwareData)
    writer.writerow(napkinData)
    writer.writerow(bagData)

    file.close()

"""
Creates inventory orders and updates menu_item.csv to reflect changes in stock.
@param int, menuItemID
@param date, date
@param int, quantity
@return float
"""
def orderMore(inventory_item_id,date,quantity):
    file =  open('inventory.csv', 'r')
    content = file.read().splitlines(True)
    line = content[inventory_item_id].split(',')

    strWithoutNewline = line[-1]
    if(inventory_item_id != (len(content) -1)):
        size = len(line[-1])
        strWithoutNewline = line[-1][:size - 1]

    line[-1]=str(quantity+int(strWithoutNewline))
    content[inventory_item_id] = ",".join(str(v) for v in line) + '\r'
    file.close()
  
    file =  open('inventory.csv', 'w')
    for i in content:
        file.write(i)
    file.close()

    global inventoryId 
    file = open('Inventory_order.csv', 'a', newline='')
    writer = csv.writer(file)
    inventoryOrder = [inventoryId, line[0], quantity, date, round(quantity*float(line[3]), 2), line[-1]]
    writer.writerow(inventoryOrder)
    file.close() #purchase price for ordering more

    

    inventoryId += 1
  
    return round(quantity*float(line[2]), 2)

if __name__ == '__main__':

    orderID = 1 #orderID will be incremented by 1 for every order of the day
    targetSales = 2200 #this is what we will change for everyday that we calulate
    gameDaySales = 3500 #this is the target sale of the gameday
    currentDay = 4
    currentDatetime = datetime.datetime(2022, 9, currentDay, 10, 00, 00) #will change with every calculated day
    salesOfTheDay = 0

    # restock all menu item for the first time
    for i in range(1, 42) : # TODO - is this still right?
        orderMore(i,currentDatetime,random.randint(300,500))


    #Week1, days 1-6
    for _ in range(6) : 
        while salesOfTheDay < targetSales:
            salesOfTheDay += createOrder(orderID, currentDatetime)
            orderID += 1
            currentDatetime = incrementDateTime(currentDatetime)
        # reset for new day
        currentDay += 1
        currentDatetime = datetime.datetime(2022, 9, currentDay, 10, 00, 00)
        salesOfTheDay = 0


    #Week1, day 7 game day (extra sales)
    while salesOfTheDay < gameDaySales:
        salesOfTheDay += createOrder(orderID, currentDatetime)
        orderID += 1
        currentDatetime = incrementDateTime(currentDatetime)

    # restock all menu item weekly # TODO - only restock the low values?
    for i in range(1, 42) :
        orderMore(i,currentDatetime,random.randint(300,500))

    # reset for new day
    currentDay += 1
    currentDatetime = datetime.datetime(2022, 9, currentDay, 10, 00, 00)
    salesOfTheDay = 0
    


    #Week2, days 1-6 
    for _ in range(6) : 
        while salesOfTheDay < targetSales:
            salesOfTheDay += createOrder(orderID, currentDatetime)
            orderID += 1
            currentDatetime = incrementDateTime(currentDatetime)
        # reset for new day
        currentDay += 1
        currentDatetime = datetime.datetime(2022, 9, currentDay, 10, 00, 00)
        salesOfTheDay = 0
 
    
    #week2, day 7 game day (extra sales)
    while salesOfTheDay < gameDaySales:
        salesOfTheDay += createOrder(orderID, currentDatetime)
        orderID += 1
        currentDatetime = incrementDateTime(currentDatetime)
    # restock all menu item weekly # TODO - only restock the low values?
    for i in range(1, 42) :
        orderMore(i,currentDatetime,random.randint(200,350))

    # reset for new day
    currentDay += 1
    currentDatetime = datetime.datetime(2022, 9, currentDay, 10, 00, 00)
    salesOfTheDay = 0


    #week3, days 1-7
    for _ in range(7) : 
        while salesOfTheDay < targetSales:
            salesOfTheDay += createOrder(orderID, currentDatetime)
            orderID += 1
            currentDatetime = incrementDateTime(currentDatetime)
        # reset for new day
        currentDay += 1
        currentDatetime = datetime.datetime(2022, 9, currentDay, 10, 00, 00)
        salesOfTheDay = 0

    # restock all menu item weekly # TODO - only restock the low values?
    for i in range(1, 42) :
        orderMore(i,currentDatetime,random.randint(200,350))