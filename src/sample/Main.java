package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jdk.internal.dynalink.beans.StaticClass;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Main extends Application {

    public static Socket c ;
    public static Stage stage1;
    public static Stage stage2;
    public static Stage stage3;
    public static Stage stage4;
    public static Stage stage5;
    public static Stage PStage;
    public static DataInputStream dis;
    public static DataOutputStream dos;
    public static Parent root;

    public Main() throws IOException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
            PStage = primaryStage;
            c = new Socket("127.0.0.1",1234);
            dis = new DataInputStream(c.getInputStream());
            dos = new DataOutputStream(c.getOutputStream());
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            PStage.setTitle("Log In");
            PStage.setScene(new Scene(root, 400, 300));
            PStage.show();
            stage1 = primaryStage;
            stage5 = primaryStage;

    }


    public static void main(String[] args) {
        launch(args);
    }

    public TextField inputID ;
    public TextField inputIPassword ;
    public TextField Registeration_Name ;
    public TextField Registeration_Balance ;
    public TextField Registeration_Bank_Name ;
    public PasswordField Registeration_Password;
    public TextField Deposite_amount ;
    public TextField Withdraw_amount ;
    public TextField Transaction_amount ;
    public TextField Transaction_another_id ;
    public TextField Transaction_Bank_Name ;
    public Button LogIn ;
    public Button Register ;
    public Button Deposite ;
    public Button Withdraw ;
    public Button Transacion ;
    public Button TransactionHistory ;
    public Button ShowDetails ;
    public Button ShowID ;
    public Button Logout ;
    public Label transs;
    public  Label MainMenu_Name ;
    public  Label MainMenu_Balance ;
    public  Label ID_label ;


    public static String ID ;
    public  String Password ;
    public  String R_Password ;
    public  String Name ;
    public  String Balance ;
    public  String Bank_Name ;
    public  String W_ammount ;
    public  String D_ammount ;
    public  String T_amount ;
    public  String T_another_id ;
    public  String T_bank_name ;
    public static String MName ;
    public  static String MBalance ;

    public void LogIn_button()  {
        try {


            ID = inputID.getText();
            Password = inputIPassword.getText();String jf="l";
            dos.writeUTF(jf);
            dos.writeUTF(ID);
            dos.writeUTF(Password);
            MName = dis.readUTF();
            if (!MName.equals("no")) {
                MBalance = dis.readUTF();
                try {
                    root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage1.setTitle("Client Control");
                stage1.setScene(new Scene(root, 600, 600));
                stage1.show();
               // MainMenu_Name.setText(MName);
                //MainMenu_Balance.setText(MBalance);
                stage2 = stage1;
            } else {
                // pop up window
            }
        }
        catch (Exception f)
        {

        }
    }

    public void Signup_button()
    {
        stage2 = (Stage) LogIn.getScene().getWindow();
        stage2.close();
        //Parent root= null;
        try {
            root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage2.setTitle("Log In");
        stage2.setScene(new Scene(root));
        stage2.show();
        stage3 = stage2 ;
    }

    public void Register_button() throws IOException {
        dos.writeUTF("r");
        MName = Registeration_Name.getText() ;
        R_Password = Registeration_Password.getText() ;
        MBalance = Registeration_Balance.getText() ;
        Bank_Name = Registeration_Bank_Name.getText() ;
        dos.writeUTF(MName);
        dos.writeUTF(R_Password);
        dos.writeUTF(MBalance);
        dos.writeUTF(Bank_Name);
        ID = dis.readUTF();
        try {
            root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage1.setTitle("Client Control");
        stage1.setScene(new Scene(root, 600, 600));
        stage1.show();
        stage2 = stage1;
    }

    public  void W_Button() throws IOException {
        dos.writeUTF("w");
        W_ammount = Withdraw_amount.getText() ;
        Withdraw_amount.clear();
        dos.writeUTF(W_ammount);
        Balance = dis.readUTF();
        if(!Balance.equals("no")) {
            MBalance = Balance;
            MainMenu_Balance.setText(MBalance);
        }
        else
        {
            //pop up
        }
    }

    public  void D_Button() throws IOException {
        dos.writeUTF("d");
        D_ammount = Deposite_amount.getText() ;
        Deposite_amount.clear();
        dos.writeUTF(D_ammount);
        String new_Balance = dis.readUTF();
        if(!new_Balance.isEmpty()) {
            MBalance = new_Balance;
            MainMenu_Balance.setText(MBalance);
        }
    }

    public  void Transaction_Button()throws IOException{
        T_amount = Transaction_amount.getText();
        T_another_id = Transaction_another_id.getText();
        T_bank_name = Transaction_Bank_Name.getText();
        Transaction_amount.clear();
        Transaction_another_id.clear();
        Transaction_Bank_Name.clear();
        dos.writeUTF("f");
        dos.writeUTF(T_amount);
        dos.writeUTF(T_another_id);
        dos.writeUTF(T_bank_name);
        String ok_or_balance = dis.readUTF();


        if (!ok_or_balance.equals("no"))
        {
            MBalance = ok_or_balance;
            MainMenu_Balance.setText(MBalance);
        }else{
            //pop window
        }

    }

    public  void Transaction_History_Button() throws IOException {
        dos.writeUTF("t");
        String history = dis.readUTF();
        transs.setText(history);
    }

    public  void Logout_Button (){

        try {
            dos.writeUTF("x");
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            PStage.setTitle("Log In");
            PStage.setScene(new Scene(root, 400, 300));
            PStage.show();
        }
        catch (Exception f){}

    }

    public void main_showdetails_button()
    {
        ID_label.setText(ID);
        MainMenu_Name.setText(MName);
        MainMenu_Balance.setText(MBalance);
    }


}
