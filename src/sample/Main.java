package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main extends Application {

    public static Socket c ;
    public static Stage PStage;
    public static DataInputStream dis;
    public static DataOutputStream dos;
    public static Parent root;


    public TextField inputID ;
    public PasswordField inputIPassword ;
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
    public Button Logout ;
    public Label transs;
    public  Label MainMenu_Name ;
    public  Label MainMenu_Balance ;
    public  Label ID_label ;


    public static String ID ;
    public  String Password ;
    public  String R_Password ;
    public  String Balance ;
    public  String Bank_Name ;
    public  String W_ammount ;
    public  String D_ammount ;
    public  String T_amount ;
    public  String T_another_id ;
    public  String T_bank_name ;
    public static String MName ;
    public  static String MBalance ;

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
    }


    public static void main(String[] args) {
        launch(args);

    }


    public void LogIn_button()  {
        try {


            ID = inputID.getText();
            Password = inputIPassword.getText();
            if(!ID.isEmpty() || !Password.isEmpty()) {
                dos.writeUTF("l");
                dos.writeUTF(ID);
                dos.writeUTF(Password);
                MName = dis.readUTF();
                if (!MName.equals("no")) {
                    PStage.close();
                    MBalance = dis.readUTF();
                    try {
                        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    PStage.setTitle("Client Control");
                    PStage.setScene(new Scene(root, 600, 400));
                    PStage.show();
                } else {
                    // pop up window
                }
            }
            else
            {
                //pop up
            }
        }
        catch (Exception f)
        {

        }
    }

    public void Signup_button()
    {
        try {
            root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PStage.setTitle("Sign Up");
        PStage.setScene(new Scene(root));
        PStage.show();
    }

    public void Register_button() throws IOException {
        MName = Registeration_Name.getText() ;
        R_Password = Registeration_Password.getText() ;
        MBalance = Registeration_Balance.getText() ;
        Bank_Name = Registeration_Bank_Name.getText() ;
        if(!(MName.isEmpty() || R_Password.isEmpty() || MBalance.isEmpty() || Bank_Name.isEmpty())) {
            dos.writeUTF("r");
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
            PStage.setTitle("Client Control");
            PStage.setScene(new Scene(root, 600, 400));
            PStage.show();
        }
        else
        {
            //pop up
        }
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
        JFrame parent = new JFrame();
        JOptionPane.showMessageDialog(parent,history,"Transaction History", JOptionPane.INFORMATION_MESSAGE);
        parent.dispose();
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

    public void main_showdetails_button() throws IOException {
        dos.writeUTF("u");
        MBalance = dis.readUTF();
        ID_label.setText(ID);
        MainMenu_Name.setText(MName);
        MainMenu_Balance.setText(MBalance);
    }


}
