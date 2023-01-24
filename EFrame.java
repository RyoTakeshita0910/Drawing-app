/**
EFrame.java
イメージの保存を行うクラス
**/
import java.awt.*;//グラフィックスに関するパッケージ
import java.awt.event.*;//eventに関するパッケージ

public class EFrame extends Frame implements ActionListener, ItemListener {
  //フィールド変数
  int formatnum=1;  //保存形式を決定する数値
  String savename="";//保存する画像の名前
  TextField txt1;// 保存するイメージの名前を打ち込むテキストフィールド
  CheckboxGroup grp1;//チェックボックス用のグループ
  Checkbox cbx1, cbx2, cbx3;//保存形式を決定するチェックボックス
  Button btE1, btE2;//フレームに属するボタン
  Panel Epnl;//メインのpanel
  Panel CBpnl;//チェックボックス用のpanel
  Panel Btpnl;//ボタン用のpanel
  Label lb1, lb2, lb3; //ラベル
  DrawingApli00 obj;//  DrawingApli00型のオブジェクト
  MyCanvas mc;//MyCanvas型のオブジェクト

// ■ コンストラクタ
  EFrame(DrawingApli00 obj, MyCanvas mc) {
    super("Save");//Frame名
    this.obj=obj;//  DrawingApli00型のオブジェクト
    this.mc=mc;//MyCanvas型のオブジェクト
    this.setBounds(150, 200, 300, 200);//フレームサイズの設定

    this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));//隙間のレイアウトを指定
    Epnl = new Panel();//Panel のオブジェクト（実体）を作成
    this.setLayout(new BorderLayout(10, 10));// レイアウト方法の指定
    this.add(Epnl,  BorderLayout.CENTER);       // 左側に mc （キャンバス）を配置
    Epnl.setLayout(new GridLayout(5,1));// ５行１列のグリッドをパネル上にさらに作成

    lb1 = new Label("保存名");//ラベル
    lb1.setBackground(Color.white);//ラベルの背景色を設定
    Epnl.add(lb1);//Epnlにlb1を追加

    txt1 = new TextField(25);         // 25文字幅設定
    txt1.addActionListener(this);     // txt1はイベント処理をする
    Epnl.add(txt1);                        // 登録する

    lb2 = new Label("形式を選んでください");//ラベル
    lb2.setBackground(Color.red);//ラベルの背景色を設定
    Epnl.add(lb2);//Epnlにlb2を追加

    CBpnl = new Panel();//Panel のオブジェクト（実体）を作成
    Epnl.add(CBpnl);//EpnlにCBpnlを追加
    CBpnl.setLayout(new GridLayout(1,4));// １行４列のグリッドをパネル上にさらに作成
    grp1 = new CheckboxGroup(); //チェックボックスのグループを生成
    lb3 = new Label("形式:");//ラベル
    CBpnl.add(lb3);//CBpnlにlb3を追加

    cbx1 = new Checkbox("png", true, grp1); ////チェックボックスの生成
    cbx1.addItemListener(this);//イベント処理
    CBpnl.add(cbx1);//CBpnlにcbx1を追加

    cbx2 = new Checkbox("jpg", false, grp1);//チェックボックスの生成
    cbx2.addItemListener(this);//イベント処理
    CBpnl.add(cbx2);//CBpnlにcbx2を追加

    cbx3 = new Checkbox("bmp", false, grp1);//チェックボックスの生成
    cbx3.addItemListener(this);//イベント処理
    CBpnl.add(cbx3);//CBpnlにcbx3を追加

    Btpnl = new Panel();//Panel のオブジェクト（実体）を作成
    Epnl.add(Btpnl);//EpnlにBtpnlを追加
    Btpnl.setLayout(new GridLayout(1,2));// １行２列のグリッドをパネル上にさらに作成

    btE1 = new Button("キャンセル");//ボタンを生成
    btE1.addActionListener(this);//イベント処理
    Btpnl.add(btE1);//BtpnlにbtE1を追加

    btE2 = new Button("OK");//ボタンを生成
    btE2.addActionListener(this);//イベント処理
    Btpnl.add(btE2);//BtpnlにbtE2を追加

    this.setVisible(true);//可視化
  }

  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == btE1){
      obj.eflg=false;//frameを表示しないときはフラグを下す
      this.setVisible(false);//フレームを閉じる
    }
    else if (e.getSource() == btE2) {
      savename = txt1.getText();//テキストフィールドの文字列を読み込み
      mc.imgexport(formatnum, savename);//画像保存をするメソッドの呼び出し
      obj.eflg=false;//frameを表示しないときはフラグを下す
      this.setVisible(false);//フレームを閉じる
    }
  }

  public void itemStateChanged(ItemEvent e) {
    //チェックボックスの選択により保存形式を決める
    if (cbx1.getState() == true) formatnum=1; //png
    if (cbx2.getState() == true) formatnum=2; //jpg
    if (cbx3.getState() == true) formatnum=3; //bmp
  }
}
