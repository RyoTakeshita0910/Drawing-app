/**
 * Simple Drawing Application
 * 簡単なお絵かきソフト
 * ・フリーハンド，直線，四角，楕円の描画機能
 * ・四角と楕円は4方向から可能
 * ・色、線の太さや形などの変更機能
 * ・テキスト入力
 * ・消しゴム
 * ・多角形の描画
 * ・全消去
 * ・ベジェ曲線の描画
 * ・undoとredoの機能
 * ・イメージ保存と画像の挿入
 * @author takeshita
 */
import java.awt.*;//グラフィックスに関するパッケージ
import java.awt.event.*;//eventに関するパッケージ

public class DrawingApli00 extends Frame implements ActionListener, ItemListener{
    // ■ フィールド変数
    CFrame Cframe;  //別途作成した Cframe クラス型の変数の宣言
    BFrame Bframe;  //別途作成した BFrame クラス型の変数の宣言
    WFrame Wframe;  //別途作成した WFrame クラス型の変数の宣言
    EFrame Eframe;  //別途作成した EFrame クラス型の変数の宣言
    IFrame Iframe;  //別途作成した IFrame クラス型の変数の宣言
    Button bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt10, bt11, bt12, bt13, bt14, bt15, bt16, bt17, bt18; // フレームに配置するボタンの宣言
    Panel  Epnl, Npnl; // ボタン配置用パネルの宣言
    Panel  Chpnl1, Chpnl2;  //チョイス用のpanel
    MyCanvas mc;               // 別途作成した MyCanvas クラス型の変数の宣言
    Choice Sqcho1, Sqcho2;  //多角形の角の数を決めるチョイス
    boolean cflg=false; //CFrameを表示している時に立つフラグ
    boolean bflg=false; //BFrameを表示している時に立つフラグ
    boolean wflg=false; //WFrameを表示している時に立つフラグ
    boolean eflg=false; //EFrameを表示している時に立つフラグ
    boolean iflg=false; //IFrameを表示している時に立つフラグ
    boolean undoflg=false;   //undoが押せない状態の時に立つフラグ
    boolean redoflg=false;  //redoが押せない状態の時に立つフラグ

    // ■ main メソッド（スタート地点）
    public static void main(String [] args) {
        DrawingApli00 da = new DrawingApli00(); //DrawingApli00を生成
    }

    // ■ コンストラクタ
    DrawingApli00() {
        super("Drawing Application");   //farameの名前
        this.setSize(800, 700); //フレームサイズの取得

        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));//隙間のレイアウトを指定

        Epnl = new Panel();// Panel のオブジェクト（実体）を作成
        Npnl = new Panel(); //Panel のオブジェクト（実体）を作成
        mc = new MyCanvas(this); // mc のオブジェクト（実体）を作成

        this.setLayout(new BorderLayout(10, 10)); // レイアウト方法の指定
        this.add(Epnl, BorderLayout.EAST);         // 右側に パネルを配置
        this.add(Npnl, BorderLayout.NORTH);       // 上側に パネルを配置
        this.add(mc,  BorderLayout.CENTER);       // 左側に mc （キャンバス）を配置
        // BorerLayout の場合，West と East の幅は
        // 部品の大きさで決まる，Center は West と East の残り幅
        Epnl.setLayout(new GridLayout(16,1));  // ボタンを配置するため，１６行１列のグリッドをパネル上にさらに作成
        bt1 = new Button("Free Hand");     bt1.addActionListener(this); Epnl.add(bt1);// ボタンを順に配置
        bt2 = new Button("/");          bt2.addActionListener(this); Epnl.add(bt2);// ボタンを順に配置
        bt3 = new Button("□");     bt3.addActionListener(this); Epnl.add(bt3);// ボタンを順に配置
        bt5 = new Button("■"); bt5.addActionListener(this); Epnl.add(bt5);// ボタンを順に配置
        bt4 = new Button("○");          bt4.addActionListener(this); Epnl.add(bt4);// ボタンを順に配置
        bt6 = new Button("●");      bt6.addActionListener(this); Epnl.add(bt6);// ボタンを順に配置
        bt15 = new Button("Polygon(△)");          bt15.addActionListener(this); Epnl.add(bt15);// ボタンを順に配置
        //choiceを設定
        Chpnl1 = new Panel(); //choice用のpanelを生成
        Epnl.add(Chpnl1); //Epnlにchoice用のpanelを追加
        Chpnl1.setLayout(new GridLayout(1,1));// レイアウト方法の指定
        Sqcho1 = new Choice();//choiceを生成
        Sqcho1.add("3");              // 項目を追加する
        Sqcho1.add("4");  // 項目を追加する
        Sqcho1.add("5");  // 項目を追加する
        Sqcho1.add("6");  // 項目を追加する
        Sqcho1.addItemListener(this);   //eventの追加
        Chpnl1.add(Sqcho1); //choiceをpanelに追加
        bt16 = new Button("Polygon(▲)");      bt16.addActionListener(this); Epnl.add(bt16);// ボタンを順に配置
        Chpnl2 = new Panel();//choice用のpanelを生成
        Epnl.add(Chpnl2);//Epnlにchoice用のpanelを追加
        Chpnl2.setLayout(new GridLayout(1,1));// レイアウト方法の指定
        Sqcho2 = new Choice();//choiceを生成
        Sqcho2.add("3");              // 項目を追加する
        Sqcho2.add("4");  // 項目を追加する
        Sqcho2.add("5");  // 項目を追加する
        Sqcho2.add("6");  // 項目を追加する
        Sqcho2.addItemListener(this); //eventの追加
        Chpnl2.add(Sqcho2);//choiceをpanelに追加
        bt17 = new Button("Curve");        bt17.addActionListener(this); Epnl.add(bt17);// ボタンを順に配置
        bt7 = new Button("Eraser");        bt7.addActionListener(this); Epnl.add(bt7);// ボタンを順に配置
        bt8 = new Button("Color");         bt8.addActionListener(this); Epnl.add(bt8);// ボタンを順に配置
        bt9 = new Button("Line");          bt9.addActionListener(this); Epnl.add(bt9);// ボタンを順に配置
        bt10 = new Button("Word");         bt10.addActionListener(this); Epnl.add(bt10);// ボタンを順に配置
        bt11 = new Button("Clear");        bt11.addActionListener(this); Epnl.add(bt11);// ボタンを順に配置


        Npnl.setLayout(new GridLayout(1,4));// ボタンを配置するため，１行4列のグリッドをパネル上にさらに作成
        bt13 = new Button("Undo");        bt13.addActionListener(this); Npnl.add(bt13);// ボタンを順に配置
        bt14 = new Button("Redo");        bt14.addActionListener(this); Npnl.add(bt14);// ボタンを順に配置
        bt18 = new Button("挿入");        bt18.addActionListener(this); Npnl.add(bt18);// ボタンを順に配置
        bt12 = new Button("保存");        bt12.addActionListener(this); Npnl.add(bt12);// ボタンを順に配置

        //xマークでwindowが閉じる処理(site参考)
        this.addWindowListener(
          new WindowAdapter(){
            public void windowClosing(WindowEvent ev){
              System.exit(0);}  // すべてのウィンドウを閉じる
          }
        );

        this.setVisible(true); //可視化
    }

    // ■ メソッド
    // ActionListener を実装しているため、例え内容が空でも必ず記述しなければならない
    public void actionPerformed(ActionEvent e){ // フレーム上で生じたイベントを e で取得
        if (e.getSource() == bt1)      // もしイベントが bt1 で生じたなら
            mc.mode=1;                   // モードを１に
        else if (e.getSource() == bt2) // もしイベントが bt2 で生じたなら
            mc.mode=2;                   // モードを２に
        else if (e.getSource() == bt3) // もしイベントが bt3 で生じたなら
            mc.mode=3;                   // モードを３に
        else if (e.getSource() == bt4) // もしイベントが bt4 で生じたなら
            mc.mode=4;                   // モードを４に
        else if (e.getSource() == bt5)// もしイベントが bt5 で生じたなら
            mc.mode=5;                      // モードを５に
        else if (e.getSource() == bt6)// もしイベントが bt6で生じたなら
            mc.mode=6;                    // モードを６に
        else if (e.getSource() == bt7)// もしイベントが bt7で生じたなら
            mc.mode=7;                  // モードを７に
        else if (e.getSource() == bt8){// もしイベントが bt8で生じたなら
          if(cflg==false){
            cflg=true;                  // CFrameを表示している時にフラグを立てる
            Cframe = new CFrame(this,mc);   //CFrameを表示
          }
        }
        else if (e.getSource() == bt9){// もしイベントが bt9で生じたなら
          if(bflg==false){
            bflg=true;                      // BFrameを表示している時にフラグを立てる
            Bframe = new BFrame(this,mc);   //BFrameを表示
          }
        }
        else if (e.getSource() == bt10){// もしイベントが bt10 で生じたなら
          mc.mode=8;                      // モードを８に
          if(wflg==false){
            wflg=true;                      // WFrameを表示している時にフラグを立てる
            Wframe = new WFrame(this,mc);   //WFrameを表示
          }
        }
        else if (e.getSource() == bt11)// もしイベントが bt11で生じたなら
            mc.CClear();
        else if (e.getSource() == bt12){// もしイベントが bt12で生じたなら
          if(eflg==false){
            eflg=true;                      // EFrameを表示している時にフラグを立てる
            Eframe = new EFrame(this,mc);     //EFrameを表示
          }
        }
        else if (e.getSource() == bt13){  /// もしイベントが bt13で生じたなら
            if(undoflg==false && mc.imgcnt > 0){
              mc.imgundo();
            }
        }
        else if (e.getSource() == bt14){  /// もしイベントが bt14で生じたなら
            if(redoflg==false && mc.undocnt > 0){
              mc.imgredo();
            }
        }
        else if(e.getSource() == bt15)// もしイベントが bt15で生じたなら
          mc.mode=15;                   // モードを１５に
        else if(e.getSource() == bt16)// もしイベントが bt16で生じたなら
          mc.mode=16;                     // モードを１６に
        else if(e.getSource() == bt17)// もしイベントが bt17 で生じたなら
          mc.mode=17;                   // モードを１７に
        else if (e.getSource() == bt18){// もしイベントが bt18 で生じたなら
          if(iflg==false){
            iflg=true;                      // IFrameを表示している時にフラグを立てる
            Iframe = new IFrame(this,mc);    //IFrameを表示
          }
        }
    }

    public void itemStateChanged(ItemEvent e) {//choiceの処理
      if (e.getItemSelectable() == Sqcho1)   mc.sqnum1=Integer.parseInt(Sqcho1.getSelectedItem());//多角形の角の数
      if (e.getItemSelectable() == Sqcho2)   mc.sqnum2=Integer.parseInt(Sqcho2.getSelectedItem());//多角形(塗りつぶし)の角の数
    }

}
