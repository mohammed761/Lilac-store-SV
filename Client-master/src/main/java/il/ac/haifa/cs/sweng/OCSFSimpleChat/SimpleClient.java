package il.ac.haifa.cs.sweng.OCSFSimpleChat;

import java.io.IOException;

import javafx.application.Platform;


import il.ac.haifa.cs.sweng.OCSFSimpleChat.ocsf.client.AbstractClient;

public class SimpleClient extends AbstractClient {
    public static MsgObject msgObject;
    private static SimpleClient client;


    public SimpleClient(String host, int port) {
        super(host, port);
    }


    @Override
    protected void handleMessageFromServer(Object msg) {

        msgObject = (MsgObject) msg;

        if (msgObject.getMsg().equals("Catalog")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("Catalog", "/Image/catalogIcon.png", "Catalogue");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("Home")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("primary", "/Image/mainPageIcon.png", "Lilac");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("contactUs")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("contactUs", "/Image/contactUsIcon.png", "Contact Us");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("specialOrdersCustomerService")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("specialOrdersCustomerService", "/Image/specialOrderIcon.png", "Special Order");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("signIn")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("SignIn", "/Image/loginIcon.png", "Sign In");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("signUp")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("SignUp", "/Image/signUpIcon.png", "Sign Up");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("signUpAccountType")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("SignUpAccountType", "/Image/signUpIcon.png", "Sign Up");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("cartUser")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("cartUser", "/Image/cartIcon.png", "Cart");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("catalogueUser")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("catalogueUser", "/Image/catalogIcon.png", "Catalogue");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("complainUser")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("complainUser", "/Image/complaintIcon.png", "Complain");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("myOrdersUser")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("myOrdersUser", "/Image/myOrdersIcon.png", "My Orders");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("detailedOrderUser")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("detailedOrderUser", "/Image/myOrdersIcon.png", "Item Of The Order");
                } catch (IOException e) {

                    e.printStackTrace();
                }
            });
        }else if (msgObject.getMsg().equals("specialOrderUser")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("specialOrderUser", "/Image/specialOrderIcon.png", "Special Order");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("primaryUser")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("primaryUser", "/Image/mainPageIcon.png", "Lilac");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("primarySystemWorker")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("primarySystemWorker", "/Image/systemWorkerIcon.png", "System Worker");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        }else if (msgObject.getMsg().equals("primaryCustomerService")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("primaryCustomerService", "/Image/customerServiceIcon.png", "Customer Service");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        }else if (msgObject.getMsg().equals("notificationsUser")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("notificationsUser", "/Image/notificationIcon.png", "Notifications");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        }else if (msgObject.getMsg().equals("primaryManager")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("primaryManager", "/Image/managerIcon.png", "System Manager");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        }else if (msgObject.getMsg().startsWith("compareHist")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("compareHist", "/Image/histogramIcon.png", "Comparison Histogram");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        }else if (msgObject.getMsg().startsWith("userDetailsUser")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("userDetailsUser", "/Image/userDetailsIcon.png", "User Details");
                } catch (IOException e) {

                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().startsWith("Histogram")) {
                Platform.runLater(() -> {
                    try {
                        App.setRoot("Histograms", "/Image/histogramIcon.png", "Single Histogram");
                    } catch (IOException e) {
                        
                        e.printStackTrace();
                    }
                });
        }else if (msgObject.getMsg().equals("primarySingleShopManager")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("primarySingleShopManager", "/Image/singleShopManagerIcon.png", "Shop Manager");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        }else if (msgObject.getMsg().equals("catalogueSystemWorker")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("catalogueSystemWorker", "/Image/catalogIcon.png", "Catalogue");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("complaintsCustomerService")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("complaintsCustomerService", "/Image/complaintIcon.png", "Complaints");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("editAccountInformation")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("changeUserDetailsUser", "/Image/editAccountIcon.png", "Edit Account");
                } catch (IOException e) {

                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("editUsersManager")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("editUsersManager", "/Image/usersIcon.png", "Edit Users");
                } catch (IOException e) {

                    e.printStackTrace();
                }
            });
        } else if (msgObject.getMsg().equals("editUserManager")) {
            Platform.runLater(() -> {
                try {
                    App.setRoot("editSpecificUserManager", "/Image/usersIcon.png", "Edit User");
                } catch (IOException e) {

                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    protected void connectionClosed() {

        // TODO Auto-generated method stub
        super.connectionClosed();

    }

    public static SimpleClient getClient() {
        if (client == null) {
            client = new SimpleClient("192.168.137.1", 3000);
        }

        return client;
    }

}
