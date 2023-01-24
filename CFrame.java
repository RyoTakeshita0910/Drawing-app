/**
CFrame.java
色の変更を行うクラス
**/
import java.awt.*;//グラフィックスに関するパッケージ
import java.awt.event.*;//eventに関するパッケージ

public class CFrame extends Frame implements AdjustmentListener,ActionListener {
  //フィールド変数
  int red=0, green=0, blue=0;
  Label lb1, lb2, lb3;
  Scrollbar sbar1, sbar2, sbar3;
  Button btC;//フレームに属するボタン
  Panel  Cpnl;//メインのpanel
  String cs="";
  MyCanvas mc;//MyCanvas型のオブジェクト
  DrawingApli00 obj;//  DrawingApli00型のオブジェクト


  CFrame(DrawingApli00 obj, MyCanvas mc){
      super("Color");//Frame名
      this.obj=obj;//  DrawingApli00型のオブジェクト
      this.mc=mc;//MyCanvas型のオブジェクト
      this.setBounds(800,0,250,300);//フレームサイズの設定
      this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));//隙間のレイアウトを指定

      Cpnl = new Panel();//Panel のオブジェクト（実体）を作成

      this.setLayout(new BorderLayout(10, 10));// レイアウト方法の指定
      this.add(Cpnl,  BorderLayout.NORTH);       // 左側に mc （キャンバス）を配置
      Cpnl.setLayout(new GridLayout(7,1));// ７行１列のグリッドをパネル上にさらに作成

      lb1 = new Label("赤(ペン)", Label.CENTER);//ラベル
      lb1.setBackground(Color.red);//ラベルの背景色を設定
      Cpnl.add(lb1);//Cpnlにlb1を追加

      sbar1 = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 256);//スクロールバーの初期設定
      sbar1.addAdjustmentListener(this);//イベント処理
      Cpnl.add(sbar1);//Cpnlにsbar1を追加

      lb2 = new Label("緑(ペン)", Label.CENTER);//ラベル
      lb2.setBackground(Color.green);//ラベルの背景色を設定
      Cpnl.add(lb2);//Cpnlにlb2を追加

      sbar2 = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 256);//スクロールバーの初期設定
      sbar2.addAdjustmentListener(this);//イベント処理
      Cpnl.add(sbar2);//Cpnlにsbar2を追加

      lb3 = new Label("青(ペン)", Label.CENTER);//ラベル
      lb3.setBackground(Color.blue);//ラベルの背景色を設定
      Cpnl.add(lb3);//Cpnlにlb3を追加

      sbar3 = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 256);//スクロールバーの初期設定
      sbar3.addAdjustmentListener(this);//イベント処理
      Cpnl.add(sbar3);//Cpnlにsbar3を追加

      red=sbar1.getValue();//スクロールバーの赤色の値を取得
      green=sbar2.getValue();//スクロールバーの緑色の値を取得
      blue=sbar3.getValue();//スクロールバーの青色の値を取得
      mc.imgPColor(red, green, blue);//色の状態を表示する文字列
      cs="ペン　赤:"+red+" 緑:"+green+" 青:"+blue;//色の状態を表示する文字列
      repaint();//再描画

      btC = new Button("OK");//ボタンを生成
      btC.addActionListener(this);//イベント処理
      Cpnl.add(btC);//CpnlにbtCを追加


      this.setVisible(true);//可視化
    }

    public void adjustmentValueChanged(AdjustmentEvent e){
      red=sbar1.getValue();//スクロールバーの赤色の値を取得
      green=sbar2.getValue();//スクロールバーの緑色の値を取得
      blue=sbar3.getValue();//スクロールバーの青色の値を取得
      mc.imgPColor(red, green, blue);//色を変更するメソッドの呼び出し
      cs="ペン　赤:"+red+" 緑:"+green+" 青:"+blue;//色の状態を表示する文字列
      repaint();//再描画
    }

    public void actionPerformed(ActionEvent e){
      if (e.getSource() == btC) {
        obj.cflg=false; //frameを表示しないときはフラグを下す
        this.setVisible(false);//フレームを閉じる
      }
    }

    public void paint(Graphics g){
      g.setColor(new Color(red, green, blue)); // パレットの色設定
      g.fillRect(10, 220, getSize().width-20, 40) ; //パレット
      g.setColor(Color.BLACK);                      //表示する文字列の色の設定
      g.drawString(cs,10,getSize().height-20);//各色の値を数値で表示
    }
}
