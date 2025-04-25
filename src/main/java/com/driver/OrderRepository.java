package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        if(!orderMap.containsKey(order.getId())){
            orderMap.put(order.getId(),order);
        }

    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        if(!partnerMap.containsKey(partnerId)){
            DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
            partnerMap.put(partnerId,deliveryPartner);
        }

    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order
            if(!partnerToOrderMap.containsKey(partnerId)){
                HashSet<String> orderSet = new HashSet<>();
                orderSet.add(orderId);
                partnerToOrderMap.put(partnerId,orderSet);
            }
            partnerToOrderMap.get(partnerId).add(orderId);
            DeliveryPartner deliveryPartner = partnerMap.get(partnerId);
            deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
            orderToPartnerMap.put(orderId,partnerId);
        }
    }

    public Order findOrderById(String orderId){
        // your code here
        if(!orderMap.containsKey(orderId)){
            return null;
        }
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        if(!partnerMap.containsKey(partnerId)){
            return null;
        }
        return partnerMap.get(partnerId);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        if(!partnerMap.containsKey(partnerId)){
            return 0;
        }
        return partnerMap.get(partnerId).getNumberOfOrders();
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        if(!partnerMap.containsKey(partnerId)){
            return null;
        }
        return new ArrayList<>(partnerToOrderMap.get(partnerId));
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        if(orderMap.isEmpty()){
            return null;
        }
        List<String> listOfOrders = new ArrayList<>();
        listOfOrders.addAll(orderMap.keySet());
        return listOfOrders;

    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        if(partnerMap.containsKey(partnerId)){
            partnerMap.remove(partnerId);
        }

    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        if(orderMap.containsKey(orderId)){
            orderMap.remove(orderId);
        }

    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        int totalOrders = orderMap.size();
        int assignedOrders = orderToPartnerMap.size();

        return totalOrders-assignedOrders;
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        if(!partnerToOrderMap.containsKey(partnerId)){
            return 0;
        }
        int ordersLeftAfterGivenTimeByPartnerId = 0;
        String[] tokens = timeString.split(":");
        int thresholdTime = (Integer.parseInt(tokens[0])*60)+Integer.parseInt(tokens[1]);
        for(String orderId : partnerToOrderMap.get(partnerId)){
            if(orderMap.get(orderId).getDeliveryTime()>thresholdTime){
                ordersLeftAfterGivenTimeByPartnerId+=1;
            }
        }
        return ordersLeftAfterGivenTimeByPartnerId;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        if(!partnerToOrderMap.containsKey(partnerId)){
            return null;
        }
        int maximumDeliveryTime = 0;
        for(String orderId : partnerToOrderMap.get(partnerId)){
            int orderDeliveryTime = orderMap.get(orderId).getDeliveryTime();
            if(orderDeliveryTime>maximumDeliveryTime){
                maximumDeliveryTime = orderDeliveryTime;
            }
        }
        String[] tokens = new String[]{String.valueOf(maximumDeliveryTime/60), String.valueOf(maximumDeliveryTime%60)};
        return String.join(":",tokens);
        // your code here
        // code should return string in format HH:MM
    }
}