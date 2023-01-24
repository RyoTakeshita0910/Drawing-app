/**
BFrame.java
線の太さや形を変更するクラス
**/
import java.awt.*;//グラフィックスに関するパッケージ
import java.awt.event.*;//eventに関するパッケージ

public class BFrame extends Frame implements AdjustmentListener,ActionListener,ItemListener {
  //フィールド変数
  CheckboxGroup grp1;//チェックボックス用のグループ
  Checkbox cbx1, cbx2, cbx3;//線の形を決定するチェックボックス
  Label lb1, lb2;//ラベル
  Scrollbar sbar1; //太さを変更するスクロールバー
  Button btB;//フレームに属するボタン
  Panel  Bpnl;//メインのpanel
  Panel  CBpnl;//チェックボックス用のpanel
  String ws="", ls1="", ls2="";
  MyCanvas mc;//MyCanvas型のオブジェクト
  DrawingApli00 obj;//  DrawingApli00型のオブジェクト
  int pwid;  //線の太さ
  int body=1; //線の形を決める値


  BFrame(DrawingApli00 obj, MyCanvas mc){
      super("Body");//Frame名
      this.mc=mc;//MyCanvas型のオブジェクト
      this.obj=obj;//  DrawingApli00型のオブジェクト
      this.setBounds(800,300,300,170);//フレームサイズの設定
      this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));//隙間のレイアウトを指定

      Bpnl = new Panel();//Panel のオブジェクト（実体）を作成
      CBpnl = new Panel();//Panel のオブジェクト（実体）を作成

      this.setLayout(new BorderLayout(10, 10));// レイアウト方法の指定
      this.add(Bpnl,  BorderLayout.NORTH);       // 左側に mc （キャンバス）を配置
      Bpnl.setLayout(new GridLayout(4,1));// ４行１列のグリッドをパネル上にさらに作成

      lb1 = new Label("太さ", Label.CENTER);//ラベル
      lb1.setBackground(Color.white);//ラベルの背景色を設定
      Bpnl.add(lb1);//Bpnlにlb1を追加

      sbar1 = new Scrollbar(Scrollbar.HORIZONTAL, 5, 1, 1, 21);//スクロールバーの初期設定
      sbar1.addAdjustmentListener(this);//イベント処理
      Bpnl.add(sbar1);//Bpnlにsbar1を追加
      pwid = sbar1.getValue();  //スクロールバーの位置から線の太さを取得
      mc.imgSize(pwid,body); //線の太さや形を変更するメソッドの呼び出し
      ws="太さ:"+pwid;  //線の太さを表示する文字列
      repaint(); //再描画

      Bpnl.add(CBpnl);//BpnlにCBpnlを追加
      CBpnl.setLayout(new GridLayout(1,1));// １行１列のグリッドをパネル上にさらに作成
      grp1 = new CheckboxGroup(); //チェックボックスのグループを生成
      lb2 = new Label("線形:");//ラベル
      CBpnl.add(lb2);//CBpnlにlb1を追加

      cbx1 = new Checkbox("角線", true, grp1); //チェックボックスの生成
      cbx1.addItemListener(this);//イベント処理
      CBpnl.add(cbx1);//CBpnlにcbx1を追加

      cbx2 = new Checkbox("丸線", false, grp1);//チェックボックスの生成
      cbx2.addItemListener(this);//イベント処理
      CBpnl.add(cbx2);//CBpnlにcbx2を追加

      cbx3 = new Checkbox("破線", false, grp1);//チェックボックスの生成
      cbx3.addItemListener(this);//イベント処理
      CBpnl.add(cbx3);//CBpnlにcbx3を追加

      btB = new Button("OK");//ボタンを生成
      btB.addActionListener(this);//イベント処理
      Bpnl.add(btB);//BpnlにbtBを追加

      this.setVisible(true);//可視化
    }

    public void adjustmentValueChanged(AdjustmentEvent e){
      pwid = sbar1.getValue();//スクロールバーの位置から線の太さを取得
      mc.imgSize(pwid,body);//線の太さや形を変更するメソッドの呼び出し
      ws="太さ:"+pwid;//線の太さを表示する文字列
      repaint();//再描画
    }

    public void actionPerformed(ActionEvent e){
      if (e.getSource() == btB) {
        obj.bflg=false;//frameを表示しないときはフラグを下す
        this.setVisible(false); //フレームを閉じる
      }
    }

    public void itemStateChanged(ItemEvent e) {
        //チェックボックスの選択により線の形を決める
      if (cbx1.getState() == true) body=1;  //角線
      if (cbx2.getState() == true) body=2;//丸線
      if (cbx3.getState() == true) body=3;//破線
      mc.imgSize(pwid,body);  //線の太さや形を変更するメソッドの呼び出し
    }

    public void paint(Graphics g){
      g.setColor(Color.BLACK);  //表示する文字列の色の設定
      g.drawString(ws, 10, 150);//線の太さを数値で表示
    }
}
