/**
WFrame.java
テキスト入力を行うクラス
**/
import java.awt.*;//グラフィックスに関するパッケージ
import java.awt.event.*;//eventに関するパッケージ

public class WFrame extends Frame implements ActionListener, ItemListener {
  //フィールド変数
  int Wbody=1;//文字列のフォントを決める数値
  int Wsize=20;//文字サイズ
  String ss="";//挿入する文字列の名前
  TextField txt1;// 挿入する文字列を打ち込むテキストフィールド
  Choice Scho;//文字サイズを決めるチョイス
  CheckboxGroup grp1;//チェックボックス用のグループ
  Checkbox cbx1, cbx2, cbx3;//字体を決定するチェックボックス
  Button btW1, btW2;//フレームに属するボタン
  Panel Wpnl;//メインのpanel
  Panel CBpnl1, Cbpnl2;//チェックボックス用のpanel
  Panel Chpnl;//チョイス用のpanel
  Label lb1, lb2, lb3;//ラベル
  DrawingApli00 obj;//  DrawingApli00型のオブジェクト
  MyCanvas mc;//MyCanvas型のオブジェクト

// ■ コンストラクタ
  WFrame(DrawingApli00 obj, MyCanvas mc) {
    super("Word");//Frame名
    this.obj=obj;//  DrawingApli00型のオブジェクト
    this.mc=mc;//MyCanvas型のオブジェクト
    this.setBounds(800, 470, 500, 200);//フレームサイズの設定

    this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));//隙間のレイアウトを指定
    Wpnl = new Panel();//Panel のオブジェクト（実体）を作成
    this.setLayout(new BorderLayout(10, 10));// レイアウト方法の指定
    this.add(Wpnl,  BorderLayout.CENTER);       // 左側に mc （キャンバス）を配置
    Wpnl.setLayout(new GridLayout(5,1));// ５行１列のグリッドをパネル上にさらに作成

    lb1 = new Label("文字入力(25文字以内)");//ラベル
    lb1.setBackground(Color.white);//ラベルの背景色を設定
    Wpnl.add(lb1);//Wpnlにlb1を追加

    txt1 = new TextField(25);         // 25文字幅設定
    txt1.addActionListener(this);     // txt1はイベント処理をする
    Wpnl.add(txt1);                        // 登録する


    CBpnl1 = new Panel();//Panel のオブジェクト（実体）を作成
    Wpnl.add(CBpnl1);//WpnlにCBpnlを追加
    CBpnl1.setLayout(new GridLayout(1,4));// １行４列のグリッドをパネル上にさらに作成
    grp1 = new CheckboxGroup(); //チェックボックスのグループを生成
    lb2 = new Label("字体:");//ラベル
    CBpnl1.add(lb2);//CBpnl1にlb2を追加

    cbx1 = new Checkbox("MS Pゴシック", true, grp1);//チェックボックスの生成
    cbx1.addItemListener(this);//イベント処理
    CBpnl1.add(cbx1);//CBpnl1にcbx1を追加

    cbx2 = new Checkbox("HGP行書体", false, grp1);//チェックボックスの生成
    cbx2.addItemListener(this);//イベント処理
    CBpnl1.add(cbx2);//CBpnl1にcbx2を追加

    cbx3 = new Checkbox("HGP創英角ポップ体", false, grp1);//チェックボックスの生成
    cbx3.addItemListener(this);//イベント処理
    CBpnl1.add(cbx3);//CBpnl1にcbx3を追加

    Chpnl = new Panel();//Panel のオブジェクト（実体）を作成
    Wpnl.add(Chpnl);//WpnlにChpnlを追加
    Chpnl.setLayout(new GridLayout(1,3));// １行３列のグリッドをパネル上にさらに作成
    Scho = new Choice();//チョイスの生成
    //文字サイズ
    Scho.add("20");
    Scho.add("30");
    Scho.add("50");
    Scho.add("75");
    Scho.add("100");
    Scho.add("150");
    Scho.addItemListener(this);//イベント処理
    Chpnl.add(Scho);//Chpnl1にschoを追加

    btW1 = new Button("実行");//ボタンを生成
    btW1.addActionListener(this);//イベント処理
    Chpnl.add(btW1);//Chpnl1にbtw1を追加

    btW2 = new Button("OK");//ボタンを生成
    btW2.addActionListener(this);//イベント処理
    Chpnl.add(btW2);//Chpnl1にbtw2を追加

    this.setVisible(true);//可視化
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == txt1) {      // txt1でEnter入力あり
      ss = txt1.getText();//テキストフィールドの文字列を読み込み
      mc.mode=8;//modeを8へ
      mc.imgText(ss,Wbody,Wsize);//テキストの挿入をするメソッドの呼び出し
    }
    else if(e.getSource() == btW1){
      ss = txt1.getText();//テキストフィールドの文字列を読み込み
      mc.mode=8;//modeを8へ
      mc.imgText(ss,Wbody,Wsize);//テキストの挿入をするメソッドの呼び出し
    }
    else if (e.getSource() == btW2) {
      obj.wflg=false;//frameを表示しないときはフラグを下す
      this.setVisible(false);//フレームを閉じる
    }
  }

  public void itemStateChanged(ItemEvent e) {
    //フォントの選択
    if (cbx1.getState() == true) Wbody=1;//MS Pゴシック
    if (cbx2.getState() == true) Wbody=2;//HGP行書体
    if (cbx3.getState() == true) Wbody=3;//HGP創英角ポップ体
    if (e.getItemSelectable() == Scho)   Wsize=Integer.parseInt(Scho.getSelectedItem());//チョイスした文字サイズに変更
    mc.imgText(ss,Wbody,Wsize);//テキストの挿入をするメソッドの呼び出し
  }
}
