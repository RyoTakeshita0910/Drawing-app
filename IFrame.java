/**
IFrame.java
画像の挿入を行うクラス
**/
import java.awt.*;//グラフィックスに関するパッケージ
import java.awt.event.*;//eventに関するパッケージ

public class IFrame extends Frame implements ActionListener {
  //フィールド変数
  String impname="";  //挿入する画像の名前
  TextField txt1;   // 挿入する画像の名前を打ち込むテキストフィールド
  Button btI1, btI2; //フレームに属するボタン
  Panel Ipnl;  //メインのpanel
  Panel Btpnl;  //ボタン用のpanel
  Label lb1, lb2, lb3;  //ラベル
  DrawingApli00 obj;  //  DrawingApli00型のオブジェクト
  MyCanvas mc;  //MyCanvas型のオブジェクト

// ■ コンストラクタ
  IFrame(DrawingApli00 obj, MyCanvas mc) {
    super("Import");  //Frame名
    this.obj=obj;//  DrawingApli00型のオブジェクト
    this.mc=mc;//MyCanvas型のオブジェクト
    this.setBounds(150, 150, 300, 150); //フレームサイズの設定

    this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));//隙間のレイアウトを指定
    Ipnl = new Panel();//Panel のオブジェクト（実体）を作成
    this.setLayout(new BorderLayout(10, 10));// レイアウト方法の指定
    this.add(Ipnl,  BorderLayout.CENTER);       // 左側に mc （キャンバス）を配置
    Ipnl.setLayout(new GridLayout(3,1));// ３行１列のグリッドをパネル上にさらに作成

    lb1 = new Label("ファイル名");  //ラベル
    lb1.setBackground(Color.white);//ラベルの背景色を設定
    Ipnl.add(lb1);//Ipnlにlb1を追加

    txt1 = new TextField(25);         // 25文字幅設定
    txt1.addActionListener(this);     // txt1はイベント処理をする
    Ipnl.add(txt1);                        // 登録する


    Btpnl = new Panel();//Panel のオブジェクト（実体）を作成
    Ipnl.add(Btpnl);//IpnlにBtpnlを追加
    Btpnl.setLayout(new GridLayout(1,2));// １行２列のグリッドをパネル上にさらに作成

    btI1 = new Button("キャンセル");//ボタンを生成
    btI1.addActionListener(this);//イベント処理
    Btpnl.add(btI1);//BtpnlにbtI1を追加

    btI2 = new Button("OK");//ボタンを生成
    btI2.addActionListener(this);//イベント処理
    Btpnl.add(btI2);//BtpnlにbtI2を追加

    this.setVisible(true);//可視化
  }

  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == btI1){
      obj.iflg=false;//frameを表示しないときはフラグを下す
      this.setVisible(false);//フレームを閉じる
    }
    else if (e.getSource() == btI2) {
      impname = txt1.getText();//テキストフィールドの文字列を読み込み
      mc.imgimport(impname);//画像の挿入をするメソッドの呼び出し
      obj.iflg=false;//frameを表示しないときはフラグを下す
      this.setVisible(false);//フレームを閉じる
    }
  }
}
