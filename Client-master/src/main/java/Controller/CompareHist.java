package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Shop;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

public class CompareHist {

    @FXML
    private BarChart<?, ?> hist;

    @FXML
    private CategoryAxis histX;

    @FXML
    private NumberAxis histY;

    @FXML
    void handleHome() throws IOException {
        App.setRoot("primaryManager", "/Image/managerIcon.png", "System Manager");
    }

    public void initialize() {
        //we need to change the name of the column and the title according to the wanted histogram
        String[] stringA = msgObject.getMsg().split(" ");


        histY.setLabel(stringA[1]);
        hist.setTitle(stringA[1]);

        List<Shop> shops = (List<Shop>) msgObject.getObject();
        List<Shop> shopList = new ArrayList<>();


        XYChart.Series set = new XYChart.Series<>();
        XYChart.Series set1 = new XYChart.Series<>();

        if (Integer.parseInt(stringA[stringA.length - 2]) < 10) {
            set.setName("shop " + stringA[stringA.length - 2] + " ");
        } else {
            set.setName("shop " + stringA[stringA.length - 2]);
        }
        set1.setName("shop " + stringA[stringA.length - 1]);

        for (int j = 0; j < shops.size(); j++) {//bubble sort according to date
            for (int i = j + 1; i < shops.size(); i++) {
                String[] strings = shops.get(j).getDate().split("-");
                String[] strings1 = shops.get(i).getDate().split("-");

                if (Integer.parseInt(strings[1]) == Integer.parseInt(strings1[1])
                        && Integer.parseInt(strings[2]) > Integer.parseInt(strings1[2])) {
                    Shop minimalShop = shops.get(i);
                    for (int k = j; k < shops.size(); k++) {
                        shopList.add(shops.get(k));
                    }

                    shopList.remove(minimalShop);
                    shops.removeAll(shopList);

                    shops.addAll(shopList);
                    shopList.removeAll(shopList);
                } else if (Integer.parseInt(strings[1]) > Integer.parseInt(strings1[1])) {
                    Shop minimalShop = shops.get(i);
                    for (int k = j; k < shops.size(); k++) {
                        shopList.add(shops.get(k));
                    }

                    shopList.remove(minimalShop);
                    shops.removeAll(shopList);

                    shops.addAll(shopList);
                    shopList.removeAll(shopList);
                }
            }
        }

        shopList.removeAll(shopList);

        for (Shop shop : shops) {
            if (shop.getShopId().equals(set.getName()))
                shopList.add(shop);
        }

        shops.removeAll(shopList);
        boolean found;

        List<String> strSet = new ArrayList<>();
        List<String> strSet1 = new ArrayList<>();
        List<String> helpStr = new ArrayList<>();

        for (Shop shop : shops) {
            found = false;
            for (Shop shop1 : shopList) {
                if (shop.getDate().equals(shop1.getDate())) {
                    found = true;
                    break;
                }
            }
            if (!found)
                strSet.add(shop.getDate());
            //set.getData().addAll(new XYChart.Data(shop.getDate(), 0));
        }

        for (Shop shop : shopList) {
            found = false;
            for (Shop shop1 : shops) {
                if (shop.getDate().equals(shop1.getDate())) {
                    found = true;
                    break;
                }
            }
            if (!found)
                strSet1.add(shop.getDate());
        }

        for (int j = 0; j < strSet1.size(); j++) {//bubble sort according to date
            for (int i = j + 1; i < strSet1.size(); i++) {
                String[] strings = strSet1.get(j).split("-");
                String[] strings1 = strSet1.get(i).split("-");

                if (Integer.parseInt(strings[1]) > Integer.parseInt(strings1[1])
                        || Integer.parseInt(strings[2]) > Integer.parseInt(strings1[2])) {
                    String minimalDate = strSet1.get(i);
                    for (int k = j; k < strSet1.size(); k++) {
                        helpStr.add(strSet1.get(k));
                    }

                    helpStr.remove(minimalDate);
                    strSet1.removeAll(helpStr);

                    strSet1.addAll(helpStr);
                    helpStr.removeAll(helpStr);
                }
            }
        }

        helpStr.removeAll(helpStr);

        for (int j = 0; j < strSet.size(); j++) {//bubble sort according to date
            for (int i = j + 1; i < strSet.size(); i++) {
                String[] strings = strSet.get(j).split("-");
                String[] strings1 = strSet.get(i).split("-");

                if (Integer.parseInt(strings[1]) > Integer.parseInt(strings1[1])
                        || Integer.parseInt(strings[2]) > Integer.parseInt(strings1[2])) {
                    String minimalDate = strSet.get(i);
                    for (int k = j; k < strSet.size(); k++) {
                        helpStr.add(strSet.get(k));
                    }

                    helpStr.remove(minimalDate);
                    strSet.removeAll(helpStr);

                    strSet.addAll(helpStr);
                    helpStr.removeAll(helpStr);
                }
            }
        }

        int i = 0, j = 0;
        while (i < shopList.size() && j < strSet.size()) {
            String[] strings = strSet.get(j).split("-");
            String[] strings1 = shopList.get(i).getDate().split("-");

            if (Integer.parseInt(strings[1]) == Integer.parseInt(strings1[1])
                    && Integer.parseInt(strings[2]) > Integer.parseInt(strings1[2])) {
                if (histY.getLabel().startsWith("Complaints"))
                    set.getData().addAll(new XYChart.Data(shopList.get(i).getDate(), shopList.get(i).getNumberOfComplaints()));
                else if (histY.getLabel().startsWith("Profit"))
                    set.getData().addAll(new XYChart.Data(shopList.get(i).getDate(), shopList.get(i).getProfit()));
                else if (histY.getLabel().startsWith("Order"))
                    set.getData().addAll(new XYChart.Data(shopList.get(i).getDate(), shopList.get(i).getNumberOfOrders()));
                i++;
            } else if (Integer.parseInt(strings[1]) > Integer.parseInt(strings1[1])) {
                if (histY.getLabel().startsWith("Complaints"))
                    set.getData().addAll(new XYChart.Data(shopList.get(i).getDate(), shopList.get(i).getNumberOfComplaints()));
                else if (histY.getLabel().startsWith("Profit"))
                    set.getData().addAll(new XYChart.Data(shopList.get(i).getDate(), shopList.get(i).getProfit()));
                else if (histY.getLabel().startsWith("Order"))
                    set.getData().addAll(new XYChart.Data(shopList.get(i).getDate(), shopList.get(i).getNumberOfOrders()));
                i++;
            } else {
                set.getData().addAll(new XYChart.Data(strSet.get(j), 0));
                j++;
            }

        }

        for (; i < shopList.size(); i++) {
            if (histY.getLabel().startsWith("Complaints"))
                set.getData().addAll(new XYChart.Data(shopList.get(i).getDate(), shopList.get(i).getNumberOfComplaints()));
            else if (histY.getLabel().startsWith("Profit"))
                set.getData().addAll(new XYChart.Data(shopList.get(i).getDate(), shopList.get(i).getProfit()));
            else if (histY.getLabel().startsWith("Order"))
                set.getData().addAll(new XYChart.Data(shopList.get(i).getDate(), shopList.get(i).getNumberOfOrders()));
        }

        for (; j < strSet.size(); j++)
            set.getData().addAll(new XYChart.Data(strSet.get(j), 0));


        //description for a problem when set = 1 and set1 = 4 the histogram is not sorted
        //here we are making the histogram //fixed
        i = 0;
        j = 0;
        while (i < shops.size() && j < strSet1.size()) {
            String[] strings = strSet1.get(j).split("-");
            String[] strings1 = shops.get(i).getDate().split("-");

            if (Integer.parseInt(strings[1]) == Integer.parseInt(strings1[1])
                    && Integer.parseInt(strings[2]) > Integer.parseInt(strings1[2])) {
                if (histY.getLabel().startsWith("Complaints"))
                    set1.getData().addAll(new XYChart.Data(shops.get(i).getDate(), shops.get(i).getNumberOfComplaints()));
                else if (histY.getLabel().startsWith("Profit"))
                    set1.getData().addAll(new XYChart.Data(shops.get(i).getDate(), shops.get(i).getProfit()));
                else if (histY.getLabel().startsWith("Order"))
                    set1.getData().addAll(new XYChart.Data(shops.get(i).getDate(), shops.get(i).getNumberOfOrders()));
                i++;
            } else if (Integer.parseInt(strings[1]) > Integer.parseInt(strings1[1])) {
                if (histY.getLabel().startsWith("Complaints"))
                    set1.getData().addAll(new XYChart.Data(shops.get(i).getDate(), shops.get(i).getNumberOfComplaints()));
                else if (histY.getLabel().startsWith("Profit"))
                    set1.getData().addAll(new XYChart.Data(shops.get(i).getDate(), shops.get(i).getProfit()));
                else if (histY.getLabel().startsWith("Order"))
                    set1.getData().addAll(new XYChart.Data(shops.get(i).getDate(), shops.get(i).getNumberOfOrders()));
                i++;
            } else {
                set1.getData().addAll(new XYChart.Data(strSet1.get(j), 0));
                j++;
            }
        }

        for (; i < shops.size(); i++) {
            if (histY.getLabel().startsWith("Complaints"))
                set1.getData().addAll(new XYChart.Data(shops.get(i).getDate(), shops.get(i).getNumberOfComplaints()));
            else if (histY.getLabel().startsWith("Profit"))
                set1.getData().addAll(new XYChart.Data(shops.get(i).getDate(), shops.get(i).getProfit()));
            else if (histY.getLabel().startsWith("Order"))
                set1.getData().addAll(new XYChart.Data(shops.get(i).getDate(), shops.get(i).getNumberOfOrders()));
        }

        for (; j < strSet1.size(); j++)
            set1.getData().addAll(new XYChart.Data(strSet1.get(j), 0));

        hist.getData().addAll(set, set1);

    }
}
