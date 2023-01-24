/*
 *  MyCanvas.java
 *  描画に関するクラス
 * @author takeshita
 */
 import java.awt.*;  //グラフィックスに関するパッケージ
 import java.awt.event.*;  //eventに関するパッケージ
 import java.awt.image.*;  //imageに関するパッケージ
 import java.io.*;   //システムの入出力用パッケージ
 import javax.imageio.*;  //画像保存で用いるパッケージ

class MyCanvas extends Canvas implements MouseListener,MouseMotionListener {
    // ■ フィールド変数
    int[] xl = new int[100];  //x座標の配列
    int[] yl = new int[100];  //y座標の配列
    int[][] sqxl = new int[100][6];  //多角形のx座標配列
    int[][] sqyl = new int[100][6];  //多角形のy座標配列
    int[][] crvxl = new int[100][4];  //ベジェ曲線のx座標配列
    int[][] crvyl = new int[100][4];  //ベジェ曲線のy座標配列
    int[] sqnuml = new int[100];  //多角形が何角形かを記憶する配列
    int[] txl = new int[100];  //丸や四角の図形の開始点のy座標配列
    int[] tyl = new int[100];  //丸や四角の図形の開始点のy座標配列
    int[] pxl = new int[100];  //図形の開始点のx座標配列
    int[] pyl = new int[100];  //図形の開始点のy座標配列
    int[] sn = new int[100];  //図形の種類を記憶させるための配列
    int[] owl = new int[100]; //図形の幅を記憶させる配列
    int[] ohl = new int[100];  //図形の高さを記憶させる配列
    int[][] fhx = new int[100][2000];  //フリーハンドのx座標配列
    int[][] fhy = new int[100][2000];  //フリーハンドのy座標配列
    int[][] fhpx = new int[100][2000];  //フリーハンドの開始点のx座標配列
    int[][] fhpy = new int[100][2000];  //フリーハンドの開始点のy座標配列
    int[] fhcl = new int[100];  //fhcntを記憶させる配列
    int[] redl = new int[100];  //赤色の値を記憶させる配列
    int[] bluel = new int[100];  //青色の値を記憶させる配列
    int[] greenl = new int[100];  //緑色の値を記憶させる配列
    int[] bodyl = new int[100];  //bodyを記憶させる配列
    int[] Wsizel = new int[100];  //文字サイズを記憶させる配列
    int[] Wbodyl = new int[100];  //Wbodyを記憶させる配列
    float[] sizel = new float[100];  //線の太さを記憶させる配列
    String[] ssl = new String[100];  //WFrameでの文字列を記憶させる配列
    int n=100;  //ベジェ曲線でのtの最大値(結線の分割数)
    int x, y;  //xy座標
    int px, py;  //直線の開始点のxy座標
    int tx, ty;  //丸や四角の図形の開始点のxy座標
    int red_v, blue_v, green_v; //色に関する変数
    int ow, oh; //図形の幅と高さ
    int mode;   // 図形の切り替えに用いる変数
    int sizenum=5; //sizeを決定するための変数
    int body=1;  //線の形を切り替えるための変数
    int Wbody=1;  //テキスト入力の字体を切り替えるための変数
    int Wsize=20;  //テキスト入力の文字サイズ
    int imgcnt = 0;  //描画された回数をカウントするカウンタ
    int crdcnt = 0;  //座標の数をカウントするカウンタ
    int micnt = 0;  //imgcntから値を引くための変数
    int fhcnt = 0;  //free handで描画された回数をカウントするカウンタ
    int undocnt=0;  //undoされた回数をカウントするカウンタ
    int sumuc = 0; //累計でundoされた回数を合計した値
    int sqrcnt = 0; //多角形が現在何角形まで描画されたかをカウントするカウンタ
    int sqnum1 = 3;  //drawPolygonにおいて何角形まで描画できるかを示す値
    int sqnum2 = 3; //fillPolygonにおいて何角形まで描画できるかを示す値
    int sqx1, sqy1, sqx2, sqy2, sqx3, sqy3; //多角形における
    int sqx4, sqy4, sqx5, sqy5, sqx6 ,sqy6; //角の座標
    float size=5.0f;  //線の太さ
    int stx, sty, enx, eny;  //paintメソッド内でのベジェ曲線の始点と終点
    int cox1, coy1, cox2, coy2;  //paintメソッド内でのベジェ曲線の方向点
    int smvx, smvy, c1mvx, c1mvy, c2mvx, c2mvy;  //paintメソッド内でのベジェ曲線における内分点
    int sc1mvx, sc1mvy, c12mvx, c12mvy;  //paintメソッド内でのベジェ曲線における内分点
    int imgsx, imgsy, imgex, imgey;  //imgpaintメソッド内でのベジェ曲線の始点と終点
    int imgco1x, imgco1y, imgco2x, imgco2y;  //imgpaintメソッド内でのベジェ曲線の方向点
    int imgsmvx, imgsmvy, imgc1mvx, imgc1mvy, imgc2mvx, imgc2mvy; //imgpaintメソッド内でのベジェ曲線における内分点
    int imgsc1x, imgsc1y, imgc12x, imgc12y;  //imgpaintメソッド内でのベジェ曲線における内分点
    int imgdsx, imgdsy, imgdex, imgdey;  //imgpaintメソッド内での描画点
    int dsx, dsy, dex, dey;  //paintメソッド内での描画点
    int t;  //ベジェ曲線において内分点の位置を変化させる変数
    int crvcnt = 0;  //ベジェ曲線において描画の状態を認知するための値
    boolean crvflg=false;  //ベジェ曲線を描画しているときにのみ立つフラグ
    boolean ucflg=false;  //undoしているときにのみ立つフラグ
    boolean sqrflg=false;  //多角形を描画しているときにのみ立つフラグ
    boolean impflg=false;  //画像挿入のときにのみ立つフラグ
    String ss = "";  //テキスト入力の文字列
    String format= null;  //イメージ保存時の保存形式
    String filename="c:\\Users\\ryoba\\Documents\\Java.jissen_Lkadai\\Ver.29\\image\\"; //イメージの保存先
    String impname ="";  //画像挿入の時に挿入する画像の名前
    Font font1, font2, font3;  //paintメソッド内でのテキスト入力の文字のフォント
    Font ifont1, ifont2, ifont3;   //imgpaintメソッド内でのテキスト入力の文字のフォント
    BufferedImage[] img = new BufferedImage[100];   // 仮の画用紙(BufferedImage配列)
    Graphics2D[] gc = new Graphics2D[100]; // 仮の画用紙用のペン(Graphics2D配列)
    BufferedImage himg;   //イメージ保存時のBufferedImage
    BufferedImage[] impimg = new BufferedImage[100];  //挿入した画像を記憶させる配列
    BasicStroke bs1, bs2, bs3;   //paintメソッド内での線の形をきめるオブジェクト
    BasicStroke bse = new BasicStroke(1.0f);  //特定の図形の過程を示すための線や円に用いる線の太さを決めるオブジェクト
    BasicStroke ibs1, ibs2, ibs3;  //imgpaintメソッド内での線の形をきめるオブジェクト
    Dimension d; // キャンバスの大きさ取得用
    DrawingApli00 obj;  //DrawingApli00型のオブジェクト


    // ■ コンストラクタ
    MyCanvas(DrawingApli00 obj){
        mode=0;                       // modeの初期化
        this.setSize(800,700);        // キャンバスのサイズを指定
        this.setBackground(Color.white);  //
        this.obj=obj;  //DrawingApli00型のオブジェクト
        addMouseListener(this);       // マウスのボタンクリックなどを監視するよう指定
        addMouseMotionListener(this); // マウスの動きを監視するよう指定
    }

    //画像挿入メソッド(画像保存の知識の応用(site参考))
    //IFrameで呼び出される
    //引数：挿入画像の名前
    public void imgimport(String impname){
      try {  //例外処理
        impimg[imgcnt-sumuc] = ImageIO.read(new File(filename+impname));  //画像の読み込み
      }catch(Exception e) {
          e.printStackTrace();
      }
      mode = 18;   //modeを18へ
      this.impname=impname;  //挿入画像の名前
      System.out.println("import");   //ログ
    }

    //画像保存メソッド(site参考)
    //EFrameで呼び出される
    //引数：保存形式を決定する変数、保存画像の名前
    public void imgexport(int formatnum, String savename){
      himg=img[imgcnt-micnt];  //保存するBufferedImage
      if(formatnum==1){   //png保存の場合
        try {  //例外処理
          ImageIO.write(himg, "png", new File(filename+savename));  //画像書き込み
        }catch(Exception e) {
            e.printStackTrace();
        }
      }
      else if(formatnum==2){  //jpg保存の場合
        try {  //例外処理
          ImageIO.write(himg, "jpeg", new File(filename+savename));  //画像書き込み
        }catch(Exception e) {
            e.printStackTrace();
        }
      }
      else if(formatnum==3){  //bmpの場合
        try {  //例外処理
          ImageIO.write(himg, "bmp", new File(filename+savename));  //画像書き込み
        }catch(Exception e) {
            e.printStackTrace();
        }
      }
      System.out.println(filename+savename);  //ログ
      System.out.println("export");  //ログ
    }

    //全消去メソッド
    public void CClear(){
        if(ucflg==true)  undoreset();   //undo後の処理
        mode=11;  //modeを11へ
        imgpaint();   //imgへの描画
        sn[imgcnt-sumuc]=11;  //図形の種類の記憶(全消去は11)
        repaint();  //paintメソッドの呼び出し
        cntplus();   //カウンタのカウント
    }

    //色を変更するメソッド
    //CFrameで呼び出される
    //引数：赤の値、緑の値、青の値
    public void imgPColor(int red_v, int green_v, int blue_v){
        this.red_v = red_v;  //赤
        this.green_v = green_v;  //緑
        this.blue_v = blue_v;  //青
    }

    //線の太さを決めるメソッド
    //BFrameで呼び出される
    //引数：線の太さを決める値、線の形を決める値
    public void imgSize(int sizenum, int body){
        this.sizenum = sizenum;   //線の太さを決める値
        this.body=body;  //線の形を決める値
        //1~20まで太さを変更できる
        if(sizenum == 1)    size=1.0f;
        else if(sizenum == 2)    size=2.0f;
        else if(sizenum == 3)    size=3.0f;
        else if(sizenum == 4)    size=4.0f;
        else if(sizenum == 5)    size=5.0f;
        else if(sizenum == 6)    size=6.0f;
        else if(sizenum == 7)    size=7.0f;
        else if(sizenum == 8)    size=8.0f;
        else if(sizenum == 9)    size=9.0f;
        else if(sizenum == 10)   size=10.0f;
        else if(sizenum == 11)   size=11.0f;
        else if(sizenum == 12)   size=12.0f;
        else if(sizenum == 13)   size=13.0f;
        else if(sizenum == 14)   size=14.0f;
        else if(sizenum == 15)   size=15.0f;
        else if(sizenum == 16)   size=16.0f;
        else if(sizenum == 17)   size=17.0f;
        else if(sizenum == 18)   size=18.0f;
        else if(sizenum == 19)   size=19.0f;
        else if(sizenum == 20)   size=20.0f;
        this.size=size;  //線の太さ
    }

    //テキスト入力
    //WFrameで呼び出される
    //引数：テキストフィールドの文字列、字体を決める値、文字サイズ
    public void imgText(String ss, int Wbody, int Wsize){
      this.Wbody=Wbody;  //字体を決める値
      this.ss=ss;   //文字列
      this.Wsize=Wsize;  //文字サイズ
      System.out.println("word in");   //ログ
    }

    //undoに関する値をリセットするメソッド
    public void undoreset(){
      obj.undoflg=false; //undoボタンを押せるようにする
      ucflg=false;  //undoしている状態から抜ける
      for(int i=1; i<=undocnt; i++){
        sn[imgcnt-sumuc-i]=0;  //最終的なundoの回数だけ図形の種類を記憶する配列を0にする
      }                        //これにより、undoした後も途中から図形を描画することができる
      sumuc += undocnt;  //累計でundoされた回数を合計した値
      System.out.println("undocnt "+undocnt);  //ログ
      System.out.println("sumuc "+sumuc);  //ログ
      undocnt=0;  //undoした回数をリセットする
    }

    //undoをするメソッド
    //DrawingApli00のbt13から呼び出される
    public void imgundo(){
      if(imgcnt-sumuc-undocnt  == 0){  //imgの配列番号が0を下回りそうになったら
        obj.undoflg=true;              //undoのボタンが押せないようになっている
      }
      else{
        ucflg=true;   //undo実行時を示すフラグ
        micnt--;     //imgcnt-micntのmicntを減らすことで1段階新しいimgを描画する
                     //(imgは新しいが描かれている図形が、1つ少ないimgを描画する)
        undocnt++;  //undoの回数をカウント
        mode = 13;  //modeを13へ
        repaint();   //paintメソッドの呼び出し
      }
      for(int i=0; i<imgcnt; i++){   //ログ
        System.out.println("sn: "+sn[i]);
      }
    }

    //redoをするメソッド
    //DrawingApli00のbt14から呼び出される
    public void imgredo(){
      if(imgcnt-sumuc-undocnt  == imgcnt){  //imgの配列数を超えそうになったら
        obj.redoflg=true;                   //redoボタンを
      }
      else{
        ucflg=true;   //undo実行中のみredoは実行可能
        micnt++;     //imgcnt-micntのmicntを増やすことで1段階古いimgを描画する
                     //(imgは古いが、描かれている図形が1つ多いimgを描画する)
        undocnt--;   //undoの回数を減らす
        mode = 13;    //modeを13へ
        repaint();   //paintメソッドの呼び出し
      }
      for(int i=0; i<imgcnt; i++){        //ログ
        System.out.println("sn: "+sn[i]);
      }
    }

    //カウンタのカウントをするメソッド
    public void cntplus(){
      imgcnt++;  //図形の数をカウント
      crdcnt++;  //座標の数をカウント
      micnt++;   //imgcntから値を引くための数値
      //ログ
      System.out.println("imgcnt: "+imgcnt);
      System.out.println("crdcnt: "+crdcnt);
      System.out.println("micnt: "+micnt);
      for(int i=0; i<imgcnt; i++){
        System.out.println("sn: "+sn[i]);
      }
    }

    //img[1]~img[imgcnt]に描画するためのメソッド
    public void imgpaint(){
      if(imgcnt > 0){  //imgcntが0の時はimgpaintは動作させない
        for(int i=1; i<=imgcnt; i++){   //img[1]~img[imgcnt]に描画
          //線の形と太さを決めるオブジェクトの生成
          ibs1 = new BasicStroke(sizel[crdcnt-i]);   //角線
          ibs2 = new BasicStroke(sizel[crdcnt-i], BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);  //丸線
          ibs3 = new BasicStroke(sizel[crdcnt-i], BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, new float[] {6}, 0);  //破線
          //線の形や太さをGrphics2Dに設定
          if(bodyl[crdcnt-i]==1)  gc[i].setStroke(ibs1);  //角線
          else if(bodyl[crdcnt-i]==2) gc[i].setStroke(ibs2);   //丸線
          else if(bodyl[crdcnt-i]==3) gc[i].setStroke(ibs3);  //破線
          //文字フォントのオブジェクトを生成
          ifont1 = new Font("MS Pゴシック", Font.PLAIN, Wsizel[crdcnt-i]);  //MS Pゴシック
          ifont2 = new Font("HGP行書体", Font.BOLD, Wsizel[crdcnt-i]);   //HGP行書体
          ifont3= new Font("HGP創英角ポップ体", Font.ITALIC, Wsizel[crdcnt-i]);   //HGP創英角ポップ体
          //色の設定
          gc[i].setColor(new Color(redl[crdcnt-i], greenl[crdcnt-i], bluel[crdcnt-i]));
          System.out.println("img"+i+" "+sn[crdcnt-i]);  //ログ
          switch(sn[crdcnt-i]){
            case 1:  //freehand
              for(int j=0; j<fhcl[crdcnt-i]; j++){
                gc[i].drawLine(fhpx[crdcnt-i][j], fhpy[crdcnt-i][j], fhx[crdcnt-i][j], fhy[crdcnt-i][j]);
              }
              System.out.println("imgpaint 1");  //ログ
              break;
            case 2:   //直線
              gc[i].drawLine(pxl[crdcnt-i], pyl[crdcnt-i], xl[crdcnt-i], yl[crdcnt-i]);
              System.out.println("imgpaint 2");//ログ
              break;
            case 3:  //四角
              gc[i].drawRect(txl[crdcnt-i], tyl[crdcnt-i], Math.abs(owl[crdcnt-i]), Math.abs(ohl[crdcnt-i]));
              System.out.println("imgpaint 3");//ログ
              break;
            case 4: //丸
              gc[i].drawOval(txl[crdcnt-i], tyl[crdcnt-i], Math.abs(owl[crdcnt-i]), Math.abs(ohl[crdcnt-i]));
              System.out.println("imgpaint 4");//ログ
              break;
            case 5://四角(塗りつぶし)
              gc[i].fillRect(txl[crdcnt-i], tyl[crdcnt-i], Math.abs(owl[crdcnt-i]), Math.abs(ohl[crdcnt-i]));
              System.out.println("imgpaint 5");//ログ
              break;
            case 6: //丸(塗りつぶし)
              gc[i].fillOval(txl[crdcnt-i], tyl[crdcnt-i], Math.abs(owl[crdcnt-i]), Math.abs(ohl[crdcnt-i]));
              System.out.println("imgpaint 6");//ログ
              break;
            case 7://消しゴム
              gc[i].setStroke(ibs2); //線に関する設定
              gc[i].setColor(Color.white);  //色の設定
              for(int j=0; j<fhcl[crdcnt-i]; j++){
                gc[i].drawLine(fhpx[crdcnt-i][j], fhpy[crdcnt-i][j], fhx[crdcnt-i][j], fhy[crdcnt-i][j]);
              }
              System.out.println("imgpaint 7");//ログ
              break;
            case 8:  //テキスト入力
              //フォント設定
              if(Wbodyl[crdcnt-i]== 1)  gc[i].setFont(ifont1);   //MS Pゴシック
              else if(Wbodyl[crdcnt-i]==2)  gc[i].setFont(ifont2);   //HGP行書体
              else if(Wbodyl[crdcnt-i]==3)  gc[i].setFont(ifont3);   //HGP創英角ポップ体
              gc[i].drawString(ssl[crdcnt-i], xl[crdcnt-i], yl[crdcnt-i]);   //文字を表示
              System.out.println("imgpaint 8");//ログ
              break;
            case 11: //全消去
              gc[i].setColor(Color.white);  //色の設定
              gc[i].fillRect(0, 0, d.width, d.height);  //白を貼り付け
              System.out.println("imgpaint 11");//ログ
              break;
            case 15:  //多角形
              gc[i].drawPolygon(sqxl[crdcnt-i], sqyl[crdcnt-i], sqnuml[crdcnt-i]);
              System.out.println("imgpaint 15");//ログ
              break;
            case 16:  //多角形(塗りつぶし)
              gc[i].fillPolygon(sqxl[crdcnt-i], sqyl[crdcnt-i], sqnuml[crdcnt-i]);
              System.out.println("imgpaint 16");//ログ
              break;
            case 17:  //ベジェ曲線
              //記憶させた配列の割り振り
              //始点
              imgsx=crvxl[crdcnt-i][0];
              imgsy=crvyl[crdcnt-i][0];
              //終点
              imgex=crvxl[crdcnt-i][2];
              imgey=crvyl[crdcnt-i][2];
              //方向点1
              imgco1x=crvxl[crdcnt-i][1];
              imgco1y=crvyl[crdcnt-i][1];
              //方向点2
              imgco2x=crvxl[crdcnt-i][3];
              imgco2y=crvyl[crdcnt-i][3];

              //tで内分点の位置を変えながらその度線を描画
              for(t=0; t<=n; t++){
                //描画点の開始点を始点に設定
                if(t==0){
                  imgdsx=imgsx;
                  imgdsy=imgsy;
                }
                //描画点の開始点を描画点の終点に設定
                else{
                  imgdsx=imgdex;
                  imgdsy=imgdey;
                }
                //内分点を求める(nは直線を分割する数)
                //始点と方向点１を結ぶ直線の内分点
                imgsmvx=(imgsx*(n-t)+imgco1x*t)/n;
                imgsmvy=(imgsy*(n-t)+imgco1y*t)/n;
                //方向点１と方向点２を結ぶ直線の内分点
                imgc1mvx=(imgco1x*(n-t)+imgco2x*t)/n;
                imgc1mvy=(imgco1y*(n-t)+imgco2y*t)/n;
                //方向点2と終点を結ぶ直線の内分点
                imgc2mvx=(imgco2x*(n-t)+imgex*t)/n;
                imgc2mvy=(imgco2y*(n-t)+imgey*t)/n;

                //（始点と方向点１を結ぶ直線の内分点）と（方向点１と方向点２を結ぶ直線の内分点)の内分点
                imgsc1x=(imgsmvx*(n-t)+imgc1mvx*t)/n;
                imgsc1y=(imgsmvy*(n-t)+imgc1mvy*t)/n;
                //（方向点１と方向点２を結ぶ直線の内分点)と(方向点2と終点を結ぶ直線の内分点)の内分点
                imgc12x=(imgc1mvx*(n-t)+imgc2mvx*t)/n;
                imgc12y=(imgc1mvy*(n-t)+imgc2mvy*t)/n;

                  //（（始点と方向点１を結ぶ直線の内分点）と（方向点１と方向点２を結ぶ直線の内分点)の内分点)と
                //（（方向点１と方向点２を結ぶ直線の内分点)と(方向点2と終点を結ぶ直線の内分点)の内分点）の内分点
                imgdex=(imgsc1x*(n-t)+imgc12x*t)/n;
                imgdey=(imgsc1y*(n-t)+imgc12y*t)/n;

                gc[i].drawLine(imgdsx, imgdsy, imgdex, imgdey);  //free handと同じ要領
              }
              System.out.println("imgpaint 17");//ログ
              break;
            case 18:  //画像の挿入
              gc[i].drawImage(impimg[crdcnt-i], txl[crdcnt-i], tyl[crdcnt-i], Math.abs(owl[crdcnt-i]), Math.abs(ohl[crdcnt-i]), this);
              System.out.println("imgpaint 18");//ログ
              break;
          }
        }
      }
    }

    // ■ メソッド（オーバーライド）
    // フレームに何らかの更新が行われた時の処理
    public void update(Graphics g) {
        paint(g); // 下記の paint を呼び出す
    }

    // ■ メソッド（オーバーライド）
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;  //Graphics g を Graphics2Dに変換
        g2.setColor(new Color(red_v, green_v, blue_v)); //色の設定
        //色の記憶
        redl[imgcnt-sumuc] = red_v;
        bluel[imgcnt-sumuc] = blue_v;
        greenl[imgcnt-sumuc] = green_v;
        //線の形や太さを記憶
        bodyl[imgcnt-sumuc] = body;
        sizel[imgcnt-sumuc] = size;
        //文字のフォントやサイズを記憶
        Wbodyl[imgcnt-sumuc] = Wbody;
        Wsizel[imgcnt-sumuc] = Wsize;
        //テキストを記憶
        ssl[imgcnt-sumuc] = ss;
        //線の形と太さを決めるオブジェクトの生成
        bs1 = new BasicStroke(size);
        bs2 = new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        bs3 = new BasicStroke(size, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, new float[] {6}, 0);
        //線の形や太さをg2に設定
        if(body== 1)   g2.setStroke(bs1);  //角線
        else if(body==2)  g2.setStroke(bs2); //丸線
        else if(body==3)  g2.setStroke(bs3);//破線
        //文字フォントのオブジェクトを生成
        font1 = new Font("MS Pゴシック", Font.PLAIN, Wsize);//MS Pゴシック
        font2 = new Font("HGP行書体", Font.BOLD, Wsize); //HGP行書体
        font3 = new Font("HGP創英角ポップ体", Font.ITALIC, Wsize);//HGP創英角ポップ体
        //フォント設定
        if(Wbody== 1)  g2.setFont(font1);//MS Pゴシック
        else if(Wbody==2)  g2.setFont(font2);//HGP行書体
        else if(Wbody==3)  g2.setFont(font3);//HGP創英角ポップ体

        d = getSize();   // キャンバスのサイズを取得

        if (img[imgcnt] == null){ // もし仮の画用紙の実体がまだ存在しなければ
          img[imgcnt] = new BufferedImage(d.width, d.height, BufferedImage.TYPE_3BYTE_BGR); // BufferedImageの作成
          gc[imgcnt] = (Graphics2D)img[imgcnt].getGraphics(); // Graphics2Dの作成
          gc[imgcnt].setColor(Color.white);  //色の設定
          gc[imgcnt].fillRect(0, 0, d.width, d.height); //背景をまっしろに
          System.out.println("img produce");// ログ
        }

        switch (mode){
          case 1: // freehand
              //線の形や太さをgcに設定
              if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
              else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
              else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);//破線
              gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc])); //色の設定
              gc[imgcnt-micnt].drawLine(px, py, x, y);  //img[imgcnt-micnt]に描画(imgcnt-micnt=0)
              g2.drawImage(img[imgcnt-micnt], 0, 0, this); //img[imgcnt-micnt]の内容を Canvas に描画
              break;
         case 2: // 直線
              g2.drawImage(img[imgcnt-micnt], 0, 0, this); //img[imgcnt-micnt]の内容を Canvas に描画
              g2.drawLine(px, py, x, y);  //canvasに直線を描画
              break;
          case 3: // 四角
              ow=x-px;  // 幅
              oh=y-py; // 高さ
              //左上以外からも図形を描くための処理
              if(x<px)  tx=x;
              else      tx=px;
              if(y<py)  ty=y;
              else      ty=py;
              g2.drawImage(img[imgcnt-micnt], 0, 0, this);  //img[imgcnt-micnt]の内容を Canvas に描画
              g2.drawRect(tx, ty, Math.abs(ow), Math.abs(oh));//canvasに四角を描画
              break;
          case 4: // 丸
              ow=x-px;// 幅
              oh=y-py;// 高さ
              //左上以外からも図形を描くための処理
              if(x<px)  tx=x;
              else      tx=px;
              if(y<py)  ty=y;
              else      ty=py;
              g2.drawImage(img[imgcnt-micnt], 0, 0, this);//img[imgcnt-micnt]の内容を Canvas に描画
              g2.drawOval(tx, ty, Math.abs(ow), Math.abs(oh));//canvasに丸を描画
              break;
          case 5:  //四角(塗りつぶし)
              ow=x-px;// 幅
              oh=y-py;// 高さ
              //左上以外からも図形を描くための処理
              if(x<px)  tx=x;
              else      tx=px;
              if(y<py)  ty=y;
              else      ty=py;
              g2.drawImage(img[imgcnt-micnt], 0, 0, this);//img[imgcnt-micnt]の内容を Canvas に描画
              g2.fillRect(tx, ty, Math.abs(ow), Math.abs(oh));//canvasに四角（塗りつぶし）を描画
              break;
          case 6://丸(塗りつぶし)
              ow=x-px;// 幅
              oh=y-py;// 高さ
              //左上以外からも図形を描くための処理
              if(x<px)  tx=x;
              else      tx=px;
              if(y<py)  ty=y;
              else      ty=py;
              g2.drawImage(img[imgcnt-micnt], 0, 0, this);//img[imgcnt-micnt]の内容を Canvas に描画
              g2.fillOval(tx, ty, Math.abs(ow), Math.abs(oh));//canvasに丸（塗りつぶし）を描画
              break;
          case 7: // 消しゴム
              //消しゴム
              gc[imgcnt-micnt].setStroke(bs2);//丸線に設定
              gc[imgcnt-micnt].setColor(Color.white);//gcの色の設定
              gc[imgcnt-micnt].drawLine(px, py, x, y);//img[imgcnt-micnt]に描画(imgcnt-micnt=0)
              g2.drawImage(img[imgcnt-micnt], 0, 0, this); // 仮の画用紙の内容を Canvas に描画
              //消しゴム描画時に現れるサークル
              g2.setColor(Color.BLACK);//g2の色の設定
              g2.setStroke(bse);//g2の線の太さを設定
              g2.drawOval(x-sizenum/2, y-sizenum/2, sizenum, sizenum);//線の太さに対応した丸をcanvasに描画
              break;
          case 8://テキスト
              g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
              g2.drawString(ss,x,y);//文字をcanvasに描画
              break;
          case 11://全消去
            gc[imgcnt-micnt].setColor(Color.white); //gcの色を白色に設定
            gc[imgcnt-micnt].fillRect(0, 0, d.width, d.height);//白に塗りつぶされた四角をimg[imgcnt-micnt]に貼り付ける
            g2.drawImage(img[imgcnt-micnt],0,0,this);// img[imgcnt-micnt]の内容を Canvas に描画
            break;
          case 13:  //Undo
            g2.drawImage(img[imgcnt-micnt],0,0,this);  // img[imgcnt-micnt]の内容を Canvas に描画
            break;                                    //micntを変更してcanvasに描画するimgを変更する
          case 15:   // draw Polygon
            if(sqrcnt==1){   //一辺目の仮の描画過程を描画
              g2.drawImage(img[imgcnt-micnt], 0, 0, this);//img[imgcnt-micnt]の内容を Canvas に描画
              g2.setStroke(bse);//g2の線の太さを設定
              g2.drawLine(sqx1, sqy1, sqx2, sqy2);//canvasに直線を描画
              break;
            }
            else if(sqrcnt==2 && sqrflg==true){  //mouseMoved処理中に描画過程を描画
              //三角形の座標配列
              int[] sqx = {sqx1, sqx2, sqx3};
              int[] sqy = {sqy1, sqy2, sqy3};

              g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
              g2.drawPolygon(sqx, sqy, 3);//canvasに三角形を描画
              break;
            }
            else if(sqnum1 > sqrcnt && sqrcnt==3 && sqrflg==true){  //mouseMoved処理中に描画過程を描画
              //四角形の座標配列
              int[] sqx = {sqx1, sqx2, sqx3, sqx4};
              int[] sqy = {sqy1, sqy2, sqy3, sqy4};

              g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
              g2.drawPolygon(sqx, sqy, 4);//canvasに四角形を描画
              break;
            }
            else if(sqnum1 > sqrcnt && sqrcnt==4 && sqrflg==true){//mouseMoved処理中に描画過程を描画
                //五角形の座標配列
              int[] sqx = {sqx1, sqx2, sqx3, sqx4, sqx5};
              int[] sqy = {sqy1, sqy2, sqy3, sqy4, sqy5};

              g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
              g2.drawPolygon(sqx, sqy, 5);//canvasに五角形を描画
              break;
            }
            else if(sqnum1 > sqrcnt && sqrcnt==5 && sqrflg==true){//mouseMoved処理中に描画過程を描画
                //六角形の座標配列
              int[] sqx = {sqx1, sqx2, sqx3, sqx4, sqx5, sqx6};
              int[] sqy = {sqy1, sqy2, sqy3, sqy4, sqy5, sqy6};

              g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
              g2.drawPolygon(sqx, sqy, 6);//canvasに六角形を描画
              break;
            }
            if(sqrflg==true && sqrcnt==sqnum1){//mouseReleasedの処理により多角形が描画し終わった時の処理
              sqrflg=false;//多角形の描画が終わるとフラグを下げる
              //多角形の座標配列
              int[] sqx = {sqx1, sqx2, sqx3, sqx4, sqx5, sqx6};
              int[] sqy = {sqy1, sqy2, sqy3, sqy4, sqy5, sqy6};

              g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
              g2.drawPolygon(sqx, sqy, sqnum1);//canvasに多角形を描画
              sqrcnt=0;//描画の過程を認識するカウンタをリセット
              break;
              }
              break;
            case 16:  //fill Polygon
              if(sqrcnt==1){//一辺目の仮の描画過程を描画
                g2.drawImage(img[imgcnt-micnt], 0, 0, this);//img[imgcnt-micnt]の内容を Canvas に描画
                g2.setStroke(bse);//g2の線の太さを設定
                g2.drawLine(sqx1, sqy1, sqx2, sqy2);//canvasに直線を描画
                break;
              }
              else if(sqrcnt==2 && sqrflg==true){//mouseMoved処理中に描画過程を描画
                  //三角形の座標配列
                int[] sqx = {sqx1, sqx2, sqx3};
                int[] sqy = {sqy1, sqy2, sqy3};

                g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
                g2.fillPolygon(sqx, sqy, 3);//canvasに三角形を描画
                break;
              }
              else if(sqnum2 > sqrcnt && sqrcnt==3 && sqrflg==true){//mouseMoved処理中に描画過程を描画
                //四角形の座標配列
                int[] sqx = {sqx1, sqx2, sqx3, sqx4};
                int[] sqy = {sqy1, sqy2, sqy3, sqy4};

                g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
                g2.fillPolygon(sqx, sqy, 4);//canvasに四角形を描画
                break;
              }
              else if(sqnum2 > sqrcnt && sqrcnt==4 && sqrflg==true){//mouseMoved処理中に描画過程を描画
                //五角形の座標配列
                int[] sqx = {sqx1, sqx2, sqx3, sqx4, sqx5};
                int[] sqy = {sqy1, sqy2, sqy3, sqy4, sqy5};

                g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
                g2.fillPolygon(sqx, sqy, 5);//canvasに五角形を描画
                break;
              }
              else if(sqnum2 > sqrcnt && sqrcnt==5 && sqrflg==true){//mouseMoved処理中に描画過程を描画
                  //六角形の座標配列
                int[] sqx = {sqx1, sqx2, sqx3, sqx4, sqx5, sqx6};
                int[] sqy = {sqy1, sqy2, sqy3, sqy4, sqy5, sqy6};

                g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
                g2.fillPolygon(sqx, sqy, 6);//canvasに六角形を描画
                break;
              }
              if(sqrflg==true && sqrcnt==sqnum2){//mouseReleasedの処理により多角形が描画し終わった時の処理
                sqrflg=false;//多角形の描画が終わるとフラグを下げる
                //多角形の座標配列
                int[] sqx = {sqx1, sqx2, sqx3, sqx4, sqx5, sqx6};
                int[] sqy = {sqy1, sqy2, sqy3, sqy4, sqy5, sqy6};
                g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
                g2.fillPolygon(sqx, sqy, sqnum2);//canvasに多角形を描画
                sqrcnt=0;//描画の過程を認識するカウンタをリセット
                break;
              }
              break;
            case 17://ベジェ曲線
              if(crvcnt==1){  //ベジェ曲線の始点と方向点１を決め、それを直線で結び、描画する
                g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
                g2.setColor(Color.BLACK);//g2の色の設定
                g2.setStroke(bse);//g2の線の太さを設定
                g2.drawLine(stx, sty, cox1, coy1);//canvasに直線を描画
                break;
              }
              if(crvflg==true && crvcnt==2){//mouseMoved処理中に描画過程を描画
                g2.drawImage(img[imgcnt-micnt], 0, 0, this);//img[imgcnt-micnt]の内容を Canvas に描画
                for(t=0; t<=n; t++){
                  //描画点の開始点を始点に設定
                  if(t==0){
                    dsx=stx;
                    dsy=sty;
                  }
                  //描画点の開始点を描画点の終点に設定
                  else{
                    dsx=dex;
                    dsy=dey;
                  }
                  //内分点を求める(nは直線を分割する数)
                  //始点と方向点１を結ぶ直線の内分点
                  smvx=(stx*(n-t)+cox1*t)/n;
                  smvy=(sty*(n-t)+coy1*t)/n;
                  //方向点1と終点を結ぶ直線の内分点
                  c1mvx=(cox1*(n-t)+enx*t)/n;
                  c1mvy=(coy1*(n-t)+eny*t)/n;
                  //（始点と方向点１を結ぶ直線の内分点）と（方向点１と終点を結ぶ直線の内分点)の内分点
                  dex=(smvx*(n-t)+c1mvx*t)/n;
                  dey=(smvy*(n-t)+c1mvy*t)/n;

                  g2.drawLine(dsx, dsy, dex, dey);//free handと同じ要領
                }
                break;
              }
              if(crvcnt==3 && crvflg==true){//mouseMoved処理中に描画過程を描画
                g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
                for(t=0; t<=n; t++){
                  if(t==0){
                    //描画点の開始点を始点に設定
                    dsx=stx;
                    dsy=sty;
                  }
                  //描画点の開始点を描画点の終点に設定
                  else{
                    dsx=dex;
                    dsy=dey;
                  }
                  //内分点を求める(nは直線を分割する数)
                  //始点と方向点１を結ぶ直線の内分点
                  smvx=(stx*(n-t)+cox1*t)/n;
                  smvy=(sty*(n-t)+coy1*t)/n;
                  //方向点１と方向点２を結ぶ直線の内分点
                  c1mvx=(cox1*(n-t)+cox2*t)/n;
                  c1mvy=(coy1*(n-t)+coy2*t)/n;
                    //方向点2と終点を結ぶ直線の内分点
                  c2mvx=(cox2*(n-t)+enx*t)/n;
                  c2mvy=(coy2*(n-t)+eny*t)/n;

                  //（始点と方向点１を結ぶ直線の内分点）と（方向点１と方向点２を結ぶ直線の内分点)の内分点
                  sc1mvx=(smvx*(n-t)+c1mvx*t)/n;
                  sc1mvy=(smvy*(n-t)+c1mvy*t)/n;
                  //（方向点１と方向点２を結ぶ直線の内分点)と(方向点2と終点を結ぶ直線の内分点)の内分点
                  c12mvx=(c1mvx*(n-t)+c2mvx*t)/n;
                  c12mvy=(c1mvy*(n-t)+c2mvy*t)/n;

                  //（（始点と方向点１を結ぶ直線の内分点）と（方向点１と方向点２を結ぶ直線の内分点)の内分点)と
                //（（方向点１と方向点２を結ぶ直線の内分点)と(方向点2と終点を結ぶ直線の内分点)の内分点）の内分点
                  dex=(sc1mvx*(n-t)+c12mvx*t)/n;
                  dey=(sc1mvy*(n-t)+c12mvy*t)/n;

                  g2.drawLine(dsx, dsy, dex, dey); //free handと同じ要領
                }
                break;
              }
              else if(crvcnt==4){//mouseReleasedの処理により多角形が描画し終わった時の処理
                System.out.println("crv OK");
                //線の形や太さをGrphics2Dに設定
                if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
                else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
                else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);//破線
                //色の設定
                gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
                for(t=0; t<=n; t++){
                  //描画点の開始点を始点に設定
                  if(t==0){
                    dsx=stx;
                    dsy=sty;
                  }
                    //描画点の開始点を描画点の終点に設定
                  else{
                    dsx=dex;
                    dsy=dey;
                  }

                  //内分点を求める(nは直線を分割する数)
                  //始点と方向点１を結ぶ直線の内分点
                  smvx=(stx*(n-t)+cox1*t)/n;
                  smvy=(sty*(n-t)+coy1*t)/n;
                  //方向点１と方向点２を結ぶ直線の内分点
                  c1mvx=(cox1*(n-t)+cox2*t)/n;
                  c1mvy=(coy1*(n-t)+coy2*t)/n;
                    //方向点2と終点を結ぶ直線の内分点
                  c2mvx=(cox2*(n-t)+enx*t)/n;
                  c2mvy=(coy2*(n-t)+eny*t)/n;

                    //（始点と方向点１を結ぶ直線の内分点）と（方向点１と方向点２を結ぶ直線の内分点)の内分点
                  sc1mvx=(smvx*(n-t)+c1mvx*t)/n;
                  sc1mvy=(smvy*(n-t)+c1mvy*t)/n;
                    //（方向点１と方向点２を結ぶ直線の内分点)と(方向点2と終点を結ぶ直線の内分点)の内分点
                  c12mvx=(c1mvx*(n-t)+c2mvx*t)/n;
                  c12mvy=(c1mvy*(n-t)+c2mvy*t)/n;

                  //（（始点と方向点１を結ぶ直線の内分点）と（方向点１と方向点２を結ぶ直線の内分点)の内分点)と
                //（（方向点１と方向点２を結ぶ直線の内分点)と(方向点2と終点を結ぶ直線の内分点)の内分点）の内分点
                  dex=(sc1mvx*(n-t)+c12mvx*t)/n;
                  dey=(sc1mvy*(n-t)+c12mvy*t)/n;

                  gc[imgcnt-micnt].drawLine(dsx, dsy, dex, dey);//free handと同じ要領で描画
                  g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
                }
                crvcnt=0;//ベジェ曲線の描画過程を認識するためのカウンタをリセット
                break;
              }
              break;
            case 18:  //砂像の挿入
              if(impflg==true){//mouseReleased後の処理
                impflg=false; //mouseReleasedされたことを確認するためのフラグ
                ow=x-px;// 幅
                oh=y-py;// 高さ
                  //左上以外からも図形を描くための処理
                if(x<px)  tx=x;
                else      tx=px;
                if(y<py)  ty=y;
                else      ty=py;
                g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
                g2.drawImage(impimg[imgcnt-sumuc], tx, ty, Math.abs(ow), Math.abs(oh), this);//挿入する画像をcanvasに描画
                break;
              }
              else{//mouseReleased前の処理
                ow=x-px;// 幅
                oh=y-py;// 高さ
                //左上以外からも図形を描くための処理
                if(x<px)  tx=x;
                else      tx=px;
                if(y<py)  ty=y;
                else      ty=py;
                g2.setColor(Color.BLACK);//g2の色の設定
                g2.setStroke(bse);//g2の線の太さを設定
                g2.drawImage(img[imgcnt-micnt], 0, 0, this);// img[imgcnt-micnt]の内容を Canvas に描画
                g2.drawRect(tx, ty, Math.abs(ow), Math.abs(oh));//挿入する画像の大きさを四角で描画
                break;
              }
        }
    }

    // ■ メソッド
    // 下記のマウス関連のメソッドは，MouseListener をインターフェースとして実装しているため
    // 例え使わなくても必ず実装しなければならない
    public void mouseClicked(MouseEvent e){} // 今回は使わないが、無いとコンパイルエラー
    public void mouseEntered(MouseEvent e){} // 今回は使わないが、無いとコンパイルエラー
    public void mouseExited(MouseEvent e){} // 今回は使わないが、無いとコンパイルエラー
    public void mousePressed(MouseEvent e){ // マウスボタンが押された時の処理
        switch (mode){
        case 1: // mode が１,７の場合，次の内容を実行する
        case 7:
            if(ucflg==true)  undoreset();  //undo後の処理
            //xy座標の取得
            x = e.getX();
            y = e.getY();
            break;
        case 2: // mode が２もしくは
        case 3: // ３もしくは
        case 4:  // 4もしくは
        case 5: // 5もしくは
        case 6: // 6の場合，次の内容を実行する
            if(ucflg==true)  undoreset(); //undo後の処理
            // 開始点のxy座標の取得
            px = e.getX();
            py = e.getY();
            //開始点のxy座標を記憶
            pxl[crdcnt-sumuc] = px;
            pyl[crdcnt-sumuc] = py;
            break;
        case 8:  // mode が8の場合，次の内容を実行する
            if(ucflg==true)  undoreset();//undo後の処理
            //xy座標の取得
            x = e.getX();
            y = e.getY();
            repaint();//paintメソッドの呼び出し
            break;
        case 15:// mode が15,16の場合，次の内容を実行する
        case 16:
            if(ucflg==true)  undoreset();//undo後の処理
            if(sqrcnt==0){  //開始点の取得
              // 開始点のxy座標の取得
              sqx1 = e.getX();
              sqy1 = e.getY();
              //開始点のxy座標を記憶
              sqxl[crdcnt-sumuc][sqrcnt] = sqx1;
              sqyl[crdcnt-sumuc][sqrcnt] = sqy1;
              //ログ
              System.out.println("sqx1: "+sqxl[crdcnt-sumuc][sqrcnt]);
              System.out.println("sqy1: "+sqyl[crdcnt-sumuc][sqrcnt]);
              System.out.println("sqrcnt: "+sqrcnt);
              sqrcnt++; //カウント
              break;
            }
            break;
        case 17:// mode が１７の場合，次の内容を実行する
          if(ucflg==true)  undoreset();//undo後の処理
          if(crvcnt==0){//開始点の取得
            // 開始点のxy座標の取得
            stx = e.getX();
            sty = e.getY();
            //開始点のxy座標を記憶
            crvxl[crdcnt-sumuc][crvcnt]=stx;
            crvyl[crdcnt-sumuc][crvcnt]=sty;
            //ログ
            System.out.println("stx: "+stx);
            System.out.println("sty: "+sty);
            System.out.println("crvcnt: "+crvcnt);
            crvcnt++;//カウント
            break;
          }
          if(crvcnt==2 && crvflg==true){//終点の取得
            crvflg=false; //mouseMoved後の処理
            //終点のxy座標の取得
            enx = e.getX();
            eny = e.getY();
            //終点のxy座標を記憶
            crvxl[crdcnt-sumuc][crvcnt]=enx;
            crvyl[crdcnt-sumuc][crvcnt]=eny;
            repaint();//paintメソッドの呼び出し
            crvcnt++;//カウント
            break;
          }
          break;
        case 18:// mode が18の場合，次の内容を実行する
          if(ucflg==true)  undoreset();//undo後の処理
          if(impflg==false){//開始点の取得
            imgimport(impname);//画像の読み込み
            // 開始点のxy座標の取得
            px = e.getX();
            py = e.getY();
              //開始点のxy座標を記憶
            pxl[crdcnt-sumuc] = px;
            pyl[crdcnt-sumuc] = py;
            break;
          }
        }
    }
    public void mouseReleased(MouseEvent e){ // マウスボタンが離された時の処理
        switch (mode){
        case 1:// mode が１の場合，次の内容を実行する
            imgpaint();//img[1]~img[imgcnt]に描画
            sn[imgcnt-sumuc]=1;//図形の種類を記憶
            // 開始点のxy座標の取得
            px = x;
            py = y;
              //開始点のxy座標を記憶
            fhpx[crdcnt-sumuc][fhcnt]=px;
            fhpy[crdcnt-sumuc][fhcnt]=py;
            //終点のxy座標の取得
            x = e.getX();
            y = e.getY();
            //終点のxy座標を記憶
            fhx[crdcnt-sumuc][fhcnt]=x;
            fhy[crdcnt-sumuc][fhcnt]=y;
            fhcnt++;//カウント
            repaint(); // 再描画
            fhcl[crdcnt-sumuc]=fhcnt;//マウスがドラッグされた回数を記憶
            cntplus();//カウンタのカウント
            fhcnt=0;//マウスがドラッグされた回数をリセット
            break;
        case 2:// mode が２の場合，次の内容を実行する
            imgpaint();//img[1]~img[imgcnt]に描画
            sn[imgcnt-sumuc]=2;;//図形の種類を記憶
              //終点のxy座標の取得
            x = e.getX();
            y = e.getY();
              //終点のxy座標を記憶
            xl[crdcnt-sumuc]=x;
            yl[crdcnt-sumuc]=y;
            //線の太さや形を設定
            if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
            else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
            else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);;//破線
            //色の設定
            gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
            gc[imgcnt-micnt].drawLine(px, py, x, y);//img[imgcnt-micnt]に直線を描画
            repaint(); // 再描画
            cntplus();//カウンタのカウント
            break;
        case 3:// mode が３の場合，次の内容を実行する
            imgpaint();//img[1]~img[imgcnt]に描画
            sn[imgcnt-sumuc]=3;//図形の種類を記憶
            //終点のxy座標の取得
            x = e.getX();
            y = e.getY();
            //終点のxy座標を記憶
            xl[crdcnt-sumuc]=x;
            yl[crdcnt-sumuc]=y;
            ow = x-px;// 幅
            oh = y-py;// 高さ
            //幅と高さの記憶
            owl[crdcnt-sumuc] = ow;
            ohl[crdcnt-sumuc] = oh;
            //左上以外からも図形を描くための処理
            if(x<px) tx=x;
            else      tx=px;
            txl[crdcnt-sumuc] = tx;//図形の開始点のx座標を記憶
            if(y<py)  ty=y;
            else      ty=py;
            tyl[crdcnt-sumuc] = ty;//図形の開始点のx座標を記憶
            //線の太さや形を設定
            if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
            else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
            else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);;//破線
            //色の設定
            gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
            gc[imgcnt-micnt].drawRect(tx, ty, Math.abs(ow), Math.abs(oh));//img[imgcnt-micnt]に四角を描画
            repaint();// 再描画
            cntplus();//カウンタのカウント
            break;
        case 4:
            imgpaint();//img[1]~img[imgcnt]に描画
            sn[imgcnt-sumuc]=4;//図形の種類を記憶
            //終点のxy座標の取得
            x = e.getX();
            y = e.getY();
            //終点のxy座標を記憶
            xl[crdcnt-sumuc]=x;
            yl[crdcnt-sumuc]=y;
            ow = x-px;// 幅
            oh = y-py;// 高さ
            //幅と高さの記憶
            owl[crdcnt-sumuc] = ow;
            ohl[crdcnt-sumuc] = oh;
              //左上以外からも図形を描くための処理
            if(x<px) tx=x;
            else      tx=px;
            txl[crdcnt-sumuc] = tx;//図形の開始点のx座標を記憶
            if(y<py)  ty=y;
            else      ty=py;
            tyl[crdcnt-sumuc] = ty;//図形の開始点のy座標を記憶
            //線の太さや形を設定
            if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
            else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
            else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);;//破線
            //色の設定
            gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
            gc[imgcnt-micnt].drawOval(tx, ty, Math.abs(ow), Math.abs(oh));//img[imgcnt-micnt]に丸を描画
            repaint();// 再描画
            cntplus();//カウンタのカウント
            break;
        case 5:
            imgpaint();//img[1]~img[imgcnt]に描画
            sn[imgcnt-sumuc]=5;//図形の種類を記憶
            //終点のxy座標の取得
            x = e.getX();
            y = e.getY();
            //終点のxy座標を記憶
            xl[crdcnt-sumuc]=x;
            yl[crdcnt-sumuc]=y;
            ow = x-px;// 幅
            oh = y-py;// 高さ
            //幅と高さの記憶
            owl[crdcnt-sumuc] = ow;
            ohl[crdcnt-sumuc] = oh;
              //左上以外からも図形を描くための処理
            if(x<px) tx=x;
            else      tx=px;
            txl[crdcnt-sumuc] = tx;//図形の開始点のx座標を記憶
            if(y<py)  ty=y;
            else      ty=py;
            tyl[crdcnt-sumuc] = ty;//図形の開始点のy座標を記憶
            //線の太さや形を設定
            if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
            else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
            else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);;//破線
            //色の設定
            gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
            gc[imgcnt-micnt].fillRect(tx, ty, Math.abs(ow), Math.abs(oh));//img[imgcnt-micnt]に四角(塗りつぶし)を描画
            repaint();// 再描画
            cntplus();//カウンタのカウント
            break;
        case 6:
            imgpaint();//img[1]~img[imgcnt]に描画
            sn[imgcnt-sumuc]=6;//図形の種類を記憶
            //終点のxy座標の取得
            x = e.getX();
            y = e.getY();
            //終点のxy座標を記憶
            xl[crdcnt-sumuc]=x;
            yl[crdcnt-sumuc]=y;
            ow = x-px;// 幅
            oh = y-py;// 高さ
            //幅と高さの記憶
            owl[crdcnt-sumuc] = ow;
            ohl[crdcnt-sumuc] = oh;
              //左上以外からも図形を描くための処理
            if(x<px) tx=x;
            else      tx=px;
            txl[crdcnt-sumuc] = tx;//図形の開始点のx座標を記憶
            if(y<py)  ty=y;
            else      ty=py;
            tyl[crdcnt-sumuc] = ty;//図形の開始点のy座標を記憶
            //線の太さや形を設定
            if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
            else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
            else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);;//破線
            //色の設定
            gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
            gc[imgcnt-micnt].fillOval(tx, ty, Math.abs(ow), Math.abs(oh));//img[imgcnt-micnt]に丸(塗りつぶし)を描画
            repaint();// 再描画
            cntplus();//カウンタのカウント
            break;
          case 7:
            imgpaint();//img[1]~img[imgcnt]に描画
            sn[imgcnt-sumuc]=7;//図形の種類を記憶
            // 開始点のxy座標の取得
            px = x;
            py = y;
            // 開始点のxy座標の記憶
            fhpx[crdcnt-sumuc][fhcnt]=px;
            fhpy[crdcnt-sumuc][fhcnt]=py;
            //終点のxy座標の取得
            x = e.getX();
            y = e.getY();
            //終点のxy座標を記憶
            fhx[crdcnt-sumuc][fhcnt]=x;
            fhy[crdcnt-sumuc][fhcnt]=y;
            fhcnt++;//カウント
            repaint(); // 再描画
            fhcl[crdcnt-sumuc]=fhcnt;//マウスがドラッグされた回数を記憶
            cntplus();//カウンタのカウント
            fhcnt=0;//マウスがドラッグされた回数をリセット
            break;
        case 8:
            imgpaint();//img[1]~img[imgcnt]に描画
            sn[imgcnt-sumuc]=8;//図形の種類を記憶
            //終点のxy座標の取得
            x = e.getX();
            y = e.getY();
            //終点のxy座標を記憶
            xl[crdcnt-sumuc]=x;
            yl[crdcnt-sumuc]=y;
            //フォントの設定
            if(Wbody== 1)  gc[imgcnt-micnt].setFont(font1);
            else if(Wbody==2)  gc[imgcnt-micnt].setFont(font2);
            else if(Wbody==3)  gc[imgcnt-micnt].setFont(font3);
            //色の設定
            gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
            gc[imgcnt-micnt].drawString(ss, x, y);//img[imgcnt-micnt]に文字を描画
            repaint();// 再描画
            cntplus();//カウンタのカウント
            break;
        case 15:   // draw Polygon
            if(sqrcnt==1){
              //多角形の2点目のxy座標を取得
              sqx2 = e.getX();
              sqy2 = e.getY();
              //多角形の2点目のxy座標を記憶
              sqxl[crdcnt-sumuc][sqrcnt]=sqx2;
              sqyl[crdcnt-sumuc][sqrcnt]=sqy2;
              //ログ
              System.out.println("sqx2: "+sqxl[crdcnt-sumuc][sqrcnt]);
              System.out.println("sqy2: "+sqyl[crdcnt-sumuc][sqrcnt]);
              System.out.println("sqrcnt: "+sqrcnt);
              sqrcnt++;//カウント
              repaint();// 再描画
              break;
            }
            if(sqnum1==3){ //三角形の場合
              if(sqrflg==true && sqrcnt==2){
                imgpaint();//img[1]~img[imgcnt]に描画
                sn[imgcnt-sumuc]=15;//図形の種類を記憶
                sqnuml[crdcnt-sumuc] = sqnum1;  //角の数を記憶
                //多角形の３点目のxy座標を取得
                sqx3 = e.getX();
                sqy3 = e.getY();
                //多角形の3点目のxy座標を記憶
                sqxl[crdcnt-sumuc][sqrcnt] = sqx3;
                sqyl[crdcnt-sumuc][sqrcnt] = sqy3;
                //ログ
                System.out.println("sqx3: "+sqxl[crdcnt-sumuc][sqrcnt]);
                System.out.println("sqy3: "+sqyl[crdcnt-sumuc][sqrcnt]);
                System.out.println("sqrcnt: "+sqrcnt);
                sqrcnt++;//カウント
                //線の太さや形を設定
                if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
                else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
                else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);;//破線
                //色の設定
                gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
                gc[imgcnt-micnt].drawPolygon(sqxl[crdcnt-sumuc], sqyl[crdcnt-sumuc], sqnum1);//img[imgcnt-micnt]に三角形を描画
                repaint();// 再描画
                cntplus();//カウンタのカウント
                break;
              }
            }
            else if(sqnum1 > 3){  //三角形より角の多い多角形の場合
              if(sqrflg==true && sqrcnt==2){
                sqrflg=false;//mouseMovedの処理をしているときに立つフラグ
                //多角形の３点目のxy座標を取得
                sqx3 = e.getX();
                sqy3 = e.getY();
                //多角形の3点目のxy座標を記憶
                sqxl[crdcnt-sumuc][sqrcnt] = sqx3;
                sqyl[crdcnt-sumuc][sqrcnt] = sqy3;
                //ログ
                System.out.println("sqx3: "+sqxl[crdcnt-sumuc][sqrcnt]);
                System.out.println("sqy3: "+sqyl[crdcnt-sumuc][sqrcnt]);
                sqrcnt++;//カウント
                repaint();// 再描画
                break;
              }
            }
            if(sqnum1==4){   //四角形の場合
              if(sqrflg==true && sqrcnt==3){
                imgpaint();//img[1]~img[imgcnt]に描画
                sn[imgcnt-sumuc]=15;//図形の種類を記憶
                sqnuml[crdcnt-sumuc] = sqnum1; //四角形の場合
                //多角形の４点目のxy座標を取得
                sqx4 = e.getX();
                sqy4 = e.getY();
                //多角形の4点目のxy座標を記憶
                sqxl[crdcnt-sumuc][sqrcnt] = sqx4;
                sqyl[crdcnt-sumuc][sqrcnt] = sqy4;
                //ログ
                System.out.println("sqx4: "+sqxl[crdcnt-sumuc][sqrcnt]);
                System.out.println("sqy4: "+sqyl[crdcnt-sumuc][sqrcnt]);
                System.out.println("sqrcnt: "+sqrcnt);
                sqrcnt++;//カウント
                //線の太さや形を設定
                if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
                else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
                else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);;//破線
                //色の設定
                gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
                gc[imgcnt-micnt].drawPolygon(sqxl[crdcnt-sumuc], sqyl[crdcnt-sumuc], sqnum1);//img[imgcnt-micnt]に四角形を描画
                repaint();// 再描画
                cntplus();//カウンタのカウント
                break;
              }
            }
            else if(sqnum1 > 4){//四角形より角の多い多角形の場合
              if(sqrflg==true && sqrcnt==3){
                sqrflg=false;;//mouseMovedの処理をしているときに立つフラグ
                  //多角形の４点目のxy座標を取得
                sqx4 = e.getX();
                sqy4 = e.getY();
                //多角形の4点目のxy座標を記憶
                sqxl[crdcnt-sumuc][sqrcnt] = sqx4;
                sqyl[crdcnt-sumuc][sqrcnt] = sqy4;
                //ログ
                System.out.println("sqx4: "+sqxl[crdcnt-sumuc][sqrcnt]);
                System.out.println("sqy4: "+sqyl[crdcnt-sumuc][sqrcnt]);
                sqrcnt++;//カウント
                repaint();// 再描画
                break;
              }
            }
            if(sqnum1==5){//五角形の場合
              if(sqrflg==true && sqrcnt==4){
                imgpaint();//img[1]~img[imgcnt]に描画
                sn[imgcnt-sumuc]=15;//図形の種類を記憶
                sqnuml[crdcnt-sumuc] = sqnum1;//角の数を記憶
                //多角形の５点目のxy座標を取得
                sqx5 = e.getX();
                sqy5 = e.getY();
                //多角形の５点目のxy座標を記憶
                sqxl[crdcnt-sumuc][sqrcnt] = sqx5;
                sqyl[crdcnt-sumuc][sqrcnt] = sqy5;
                //ログ
                System.out.println("sqx5: "+sqxl[crdcnt-sumuc][sqrcnt]);
                System.out.println("sqy5: "+sqyl[crdcnt-sumuc][sqrcnt]);
                System.out.println("sqrcnt: "+sqrcnt);
                sqrcnt++;//カウント
                //線の太さや形を設定
                if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
                else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
                else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);;//破線
                //色の設定
                gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
                gc[imgcnt-micnt].drawPolygon(sqxl[crdcnt-sumuc], sqyl[crdcnt-sumuc], sqnum1);//img[imgcnt-micnt]に五角形を描画
                repaint();// 再描画
                cntplus();//カウンタのカウント
                break;
              }
            }
            else if(sqnum1 > 5){//五角形より角の多い多角形の場合
              if(sqrflg==true && sqrcnt==4){
                sqrflg=false;//mouseMovedの処理をしているときに立つフラグ
                //多角形の５点目のxy座標を取得
                sqx5 = e.getX();
                sqy5 = e.getY();
                //多角形の５点目のxy座標を記憶
                sqxl[crdcnt-sumuc][sqrcnt] = sqx5;
                sqyl[crdcnt-sumuc][sqrcnt] = sqy5;
                //ログ
                System.out.println("sqx5: "+sqxl[crdcnt-sumuc][sqrcnt]);
                System.out.println("sqy5: "+sqyl[crdcnt-sumuc][sqrcnt]);
                sqrcnt++;//カウント
                repaint();// 再描画
                break;
              }
            }
            if(sqrflg==true && sqrcnt==5){//六角形の場合
                imgpaint();//img[1]~img[imgcnt]に描画
                sn[imgcnt-sumuc]=15;//図形の種類を記憶
                sqnuml[crdcnt-sumuc] = sqnum1;//角の数を記憶
                //多角形の６点目のxy座標を取得
                sqx6 = e.getX();
                sqy6 = e.getY();
                  //多角形の６点目のxy座標を記憶
                sqxl[crdcnt-sumuc][sqrcnt] = sqx6;
                sqyl[crdcnt-sumuc][sqrcnt] = sqy6;
                //ログ
                System.out.println("sqx6: "+sqxl[crdcnt-sumuc][sqrcnt]);
                System.out.println("sqy6: "+sqyl[crdcnt-sumuc][sqrcnt]);
                System.out.println("sqrcnt: "+sqrcnt);
                sqrcnt++;//カウント
                //線の太さや形を設定
                if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
                else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
                else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);;//破線
                //色の設定
                gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
                gc[imgcnt-micnt].drawPolygon(sqxl[crdcnt-sumuc], sqyl[crdcnt-sumuc], sqnum1);//img[imgcnt-micnt]に六角形を描画
                repaint();// 再描画
                cntplus();//カウンタのカウント
                break;
            }
            case 16:   // fill Polygon
                if(sqrcnt==1){
                  //多角形の2点目のxy座標を取得
                  sqx2 = e.getX();
                  sqy2 = e.getY();
                    //多角形の2点目のxy座標を記憶
                  sqxl[crdcnt-sumuc][sqrcnt]=sqx2;
                  sqyl[crdcnt-sumuc][sqrcnt]=sqy2;
                  //ログ
                  System.out.println("sqx2: "+sqxl[crdcnt-sumuc][sqrcnt]);
                  System.out.println("sqy2: "+sqyl[crdcnt-sumuc][sqrcnt]);
                  System.out.println("sqrcnt: "+sqrcnt);
                  sqrcnt++;//カウント
                  repaint();// 再描画
                  break;
                }
                if(sqnum2==3){//三角形の場合
                  if(sqrflg==true && sqrcnt==2){
                    imgpaint();//img[1]~img[imgcnt]に描画
                    sn[imgcnt-sumuc]=16;//図形の種類を記憶
                    sqnuml[crdcnt-sumuc] = sqnum2; //角の数を記憶
                      //多角形の３点目のxy座標を取得
                    sqx3 = e.getX();
                    sqy3 = e.getY();
                    //多角形の3点目のxy座標を記憶
                    sqxl[crdcnt-sumuc][sqrcnt] = sqx3;
                    sqyl[crdcnt-sumuc][sqrcnt] = sqy3;
                    //ログ
                    System.out.println("sqx3: "+sqxl[crdcnt-sumuc][sqrcnt]);
                    System.out.println("sqy3: "+sqyl[crdcnt-sumuc][sqrcnt]);
                    System.out.println("sqrcnt: "+sqrcnt);
                    sqrcnt++;//カウント
                    //線の太さや形を設定
                    if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
                    else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
                    else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);;//破線
                    //色の設定
                    gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
                    gc[imgcnt-micnt].fillPolygon(sqxl[crdcnt-sumuc], sqyl[crdcnt-sumuc], sqnum2);//img[imgcnt-micnt]に三角形を描画
                    repaint();// 再描画
                    cntplus();//カウンタのカウント
                    break;
                  }
                }
                else if(sqnum2 > 3){//三角形より角の多い多角形の場合
                  if(sqrflg==true && sqrcnt==2){
                    sqrflg=false;//mouseMovedの処理をしているときに立つフラグ
                    //多角形の３点目のxy座標を取得
                    sqx3 = e.getX();
                    sqy3 = e.getY();
                    //多角形の3点目のxy座標を記憶
                    sqxl[crdcnt-sumuc][sqrcnt] = sqx3;
                    sqyl[crdcnt-sumuc][sqrcnt] = sqy3;
                    //ログ
                    System.out.println("sqx3: "+sqxl[crdcnt-sumuc][sqrcnt]);
                    System.out.println("sqy3: "+sqyl[crdcnt-sumuc][sqrcnt]);
                    sqrcnt++;//カウント
                    repaint();// 再描画
                    break;
                  }
                }
                if(sqnum2==4){ //四角形の場合
                  if(sqrflg==true && sqrcnt==3){
                    imgpaint();//img[1]~img[imgcnt]に描画
                    sn[imgcnt-sumuc]=16;//図形の種類を記憶
                    sqnuml[crdcnt-sumuc] = sqnum2; //四角形の場合
                    //多角形の４点目のxy座標を取得
                    sqx4 = e.getX();
                    sqy4 = e.getY();
                    //多角形の4点目のxy座標を記憶
                    sqxl[crdcnt-sumuc][sqrcnt] = sqx4;
                    sqyl[crdcnt-sumuc][sqrcnt] = sqy4;
                    //ログ
                    System.out.println("sqx4: "+sqxl[crdcnt-sumuc][sqrcnt]);
                    System.out.println("sqy4: "+sqyl[crdcnt-sumuc][sqrcnt]);
                    System.out.println("sqrcnt: "+sqrcnt);
                    sqrcnt++;//カウント
                    //線の太さや形を設定
                    if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
                    else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
                    else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);;//破線
                    //色の設定
                    gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
                    gc[imgcnt-micnt].fillPolygon(sqxl[crdcnt-sumuc], sqyl[crdcnt-sumuc], sqnum2);//img[imgcnt-micnt]に四角形を描画
                    repaint();// 再描画
                    cntplus();//カウンタのカウント
                    break;
                  }
                }
                else if(sqnum2 > 4){//四角形より角の多い多角形の場合
                  if(sqrflg==true && sqrcnt==3){
                    sqrflg=false;//mouseMovedの処理をしているときに立つフラグ
                    //多角形の４点目のxy座標を取得
                    sqx4 = e.getX();
                    sqy4 = e.getY();
                      //多角形の4点目のxy座標を記憶
                    sqxl[crdcnt-sumuc][sqrcnt] = sqx4;
                    sqyl[crdcnt-sumuc][sqrcnt] = sqy4;
                    //ログ
                    System.out.println("sqx4: "+sqxl[crdcnt-sumuc][sqrcnt]);
                    System.out.println("sqy4: "+sqyl[crdcnt-sumuc][sqrcnt]);
                    sqrcnt++;//カウント
                    repaint();// 再描画
                    break;
                  }
                }
                if(sqnum2==5){//五角形の場合
                  if(sqrflg==true && sqrcnt==4){
                    imgpaint();//img[1]~img[imgcnt]に描画
                    sn[imgcnt-sumuc]=16;//図形の種類を記憶
                    sqnuml[crdcnt-sumuc] = sqnum2;;//角の数を記憶
                    //多角形の５点目のxy座標を取得
                    sqx5 = e.getX();
                    sqy5 = e.getY();
                    //多角形の５点目のxy座標を記憶
                    sqxl[crdcnt-sumuc][sqrcnt] = sqx5;
                    sqyl[crdcnt-sumuc][sqrcnt] = sqy5;
                    //ログ
                    System.out.println("sqx5: "+sqxl[crdcnt-sumuc][sqrcnt]);
                    System.out.println("sqy5: "+sqyl[crdcnt-sumuc][sqrcnt]);
                    System.out.println("sqrcnt: "+sqrcnt);
                    sqrcnt++;//カウント
                    //線の太さや形を設定
                    if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
                    else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
                    else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);;//破線
                    //色の設定
                    gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
                    gc[imgcnt-micnt].fillPolygon(sqxl[crdcnt-sumuc], sqyl[crdcnt-sumuc], sqnum2);//img[imgcnt-micnt]に五角形を描画
                    repaint();// 再描画
                    cntplus();//カウンタのカウント
                    break;
                  }
                }
                else if(sqnum2 > 5){//五角形より角の多い多角形の場合
                  if(sqrflg==true && sqrcnt==4){
                    sqrflg=false;//mouseMovedの処理をしているときに立つフラグ
                    //多角形の５点目のxy座標を取得
                    sqx5 = e.getX();
                    sqy5 = e.getY();
                      //多角形の５点目のxy座標を記憶
                    sqxl[crdcnt-sumuc][sqrcnt] = sqx5;
                    sqyl[crdcnt-sumuc][sqrcnt] = sqy5;
                    //ログ
                    System.out.println("sqx5: "+sqxl[crdcnt-sumuc][sqrcnt]);
                    System.out.println("sqy5: "+sqyl[crdcnt-sumuc][sqrcnt]);
                    sqrcnt++;//カウント
                    repaint();// 再描画
                    break;
                  }
                }
                if(sqrflg==true && sqrcnt==5){//六角形の場合
                    imgpaint();//img[1]~img[imgcnt]に描画
                    sn[imgcnt-sumuc]=16;//図形の種類を記憶
                    sqnuml[crdcnt-sumuc] = sqnum2;//角の数を記憶
                    //多角形の６点目のxy座標を取得
                    sqx6 = e.getX();
                    sqy6 = e.getY();
                    //多角形の６点目のxy座標を記憶
                    sqxl[crdcnt-sumuc][sqrcnt] = sqx6;
                    sqyl[crdcnt-sumuc][sqrcnt] = sqy6;
                    //ログ
                    System.out.println("sqx6: "+sqxl[crdcnt-sumuc][sqrcnt]);
                    System.out.println("sqy6: "+sqyl[crdcnt-sumuc][sqrcnt]);
                    System.out.println("sqrcnt: "+sqrcnt);
                    sqrcnt++;//カウント
                    //線の太さや形を設定
                    if(body== 1)  gc[imgcnt-micnt].setStroke(bs1);//角線
                    else if(body==2)  gc[imgcnt-micnt].setStroke(bs2);//丸線
                    else if(body==3)  gc[imgcnt-micnt].setStroke(bs3);;//破線
                    //色の設定
                    gc[imgcnt-micnt].setColor(new Color(redl[crdcnt-sumuc], greenl[crdcnt-sumuc], bluel[crdcnt-sumuc]));
                    gc[imgcnt-micnt].fillPolygon(sqxl[crdcnt-sumuc], sqyl[crdcnt-sumuc], sqnum2);//img[imgcnt-micnt]に六角形を描画
                    repaint();// 再描画
                    cntplus();//カウンタのカウント
                    break;
                }
              case 17:
                if(crvcnt==1){
                  //ベジェ曲線の方向点１のxy座標を取得
                  cox1 = e.getX();
                  coy1 = e.getY();
                  //ベジェ曲線の方向点１のxy座標を記憶
                  crvxl[crdcnt-sumuc][crvcnt]=cox1;
                  crvyl[crdcnt-sumuc][crvcnt]=coy1;
                  //ログ
                  System.out.println("cox1: "+cox1);
                  System.out.println("coy1: "+coy1);
                  System.out.println("crvcnt: "+crvcnt);
                  crvcnt++;//カウント
                  repaint();// 再描画
                  break;
                }
                if(crvcnt==2 && crvflg==true){
                  imgpaint();//img[1]~img[imgcnt]に描画
                  sn[imgcnt-sumuc]=17;//図形の種類を記憶
                  crvflg=false;//mouseMovedの処理をしているときに立つフラグ
                  //ベジェ曲線の終点のxy座標を取得
                  enx = e.getX();
                  eny = e.getY();
                  //ベジェ曲線の終点のxy座標を記憶
                  crvxl[crdcnt-sumuc][crvcnt]=enx;
                  crvyl[crdcnt-sumuc][crvcnt]=eny;
                  crvcnt++;//カウント
                  repaint();// 再描画
                  cntplus();//カウンタのカウント
                  break;
                }
                if(crvcnt==3){
                  imgpaint();//img[1]~img[imgcnt]に描画
                  sn[imgcnt-sumuc]=17;//図形の種類を記憶
                  crvflg=false;//mouseMovedの処理をしているときに立つフラグ
                    //ベジェ曲線の方向点２のxy座標を取得
                  cox2 = e.getX();
                  coy2 = e.getY();
                  //ベジェ曲線の方向点２のxy座標を記憶
                  crvxl[crdcnt-sumuc][crvcnt]=cox2;
                  crvyl[crdcnt-sumuc][crvcnt]=coy2;
                  crvcnt++;//カウント
                  System.out.println("crvcnt: "+crvcnt);//ログ
                  repaint();// 再描画
                  cntplus();//カウンタのカウント
                  break;
                }
                case 18:
                  if(impflg==false){
                    impflg=true;//mouseReleasedがされたかを認識するためのフラグ
                    imgpaint();//img[1]~img[imgcnt]に描画
                    sn[imgcnt-sumuc]=18;//図形の種類を記憶
                    //終点のxy座標の取得
                    x = e.getX();
                    y = e.getY();
                    //終点のxy座標を記憶
                    xl[crdcnt-sumuc]=x;
                    yl[crdcnt-sumuc]=y;
                    //幅と高さの記憶
                    ow = x-px;// 幅
                    oh = y-py;// 高さ
                    //幅と高さの記憶
                    owl[crdcnt-sumuc] = ow;
                    ohl[crdcnt-sumuc] = oh;
                    //左上以外からも図形を描くための処理
                    if(x<px) tx=x;
                    else      tx=px;
                    txl[crdcnt-sumuc] = tx;//図形の開始点のx座標を記憶
                    if(y<py)  ty=y;
                    else      ty=py;
                    tyl[crdcnt-sumuc] = ty;//図形の開始点のy座標を記憶
                    gc[imgcnt-micnt].drawImage(impimg[imgcnt-sumuc], tx, ty, Math.abs(ow), Math.abs(oh), this);//挿入する画像をimg[imgcnt-micnt]に描画
                    repaint();// 再描画
                    cntplus();//カウンタのカウント
                    break;
                  }
        }
    }

    // ■ メソッド
    // 下記のマウス関連のメソッドは，MouseMotionListener をインターフェースとして実装しているため
    // 例え使わなくても必ず実装しなければならない
    public void mouseDragged(MouseEvent e){ // マウスがドラッグされたの処理
        switch (mode){
        case 1: // mode が１と７の場合，次の内容を実行する
        case 7:
            px = x;
            py = y;
            fhpx[crdcnt-sumuc][fhcnt]=px;
            fhpy[crdcnt-sumuc][fhcnt]=py;
            x = e.getX();
            y = e.getY();
            fhx[crdcnt-sumuc][fhcnt]=x;
            fhy[crdcnt-sumuc][fhcnt]=y;
            fhcnt++;//カウント
            repaint(); // 再描画
            break;
        case 2: // mode が２もしくは
        case 3: // ３もしくは
        case 4: // ４もしくは
        case 5:// ５もしくは
        case 6://６もしくは
        case 8://８の場合，次の内容を実行する
            //終点のxy座標の取得
            x = e.getX();
            y = e.getY();
            repaint();// 再描画
            break;
        case 15:
        case 16:
          if(sqrcnt==1){
            //多角形の2点目のxy座標を取得
            sqx2 = e.getX();
            sqy2 = e.getY();
            repaint();// 再描画
            break;
          }
          break;
        case 17:
          if(crvcnt==1){
            //ベジェ曲線の方向点１のxy座標を取得
            cox1 = e.getX();
            coy1 = e.getY();
            repaint();// 再描画
            break;
          }
          else if(crvcnt==3){
            crvflg=true;//mouseMovedの処理をしているときに立つフラグ
            //ベジェ曲線の方向点２のxy座標を取得
            cox2 = e.getX();
            coy2 = e.getY();
            repaint();// 再描画
            break;
          }
          break;
      case 18:
        if(impflg==false){
          //終点のxy座標の取得
          x = e.getX();
          y = e.getY();
          repaint();// 再描画
          break;
        }
      }
    }
    public void mouseMoved(MouseEvent e){
      switch(mode){
        case 15:
          if(sqrcnt==2){
            sqrflg=true;//mouseMovedの処理をしているときに立つフラグ
            //多角形の３点目のxy座標を取得
            sqx3 = e.getX();
            sqy3 = e.getY();
            repaint();// 再描画
            break;
          }
          if(sqnum1 > sqrcnt && sqrcnt==3){
            sqrflg=true;//mouseMovedの処理をしているときに立つフラグ
            //多角形の４点目のxy座標を取得
            sqx4 = e.getX();
            sqy4 = e.getY();
            repaint();// 再描画
            break;
          }
          if(sqnum1 > sqrcnt && sqrcnt==4){
            sqrflg=true;//mouseMovedの処理をしているときに立つフラグ
            //多角形の５点目のxy座標を取得
            sqx5 = e.getX();
            sqy5 = e.getY();
            repaint();// 再描画
            break;
          }
          if(sqnum1 > sqrcnt && sqrcnt==5){
            sqrflg=true;//mouseMovedの処理をしているときに立つフラグ
            //多角形の６点目のxy座標を取得
            sqx6 = e.getX();
            sqy6 = e.getY();
            repaint();// 再描画
            break;
          }
          break;
        case 16:
            if(sqrcnt==2){
              sqrflg=true;//mouseMovedの処理をしているときに立つフラグ
              //多角形の３点目のxy座標を取得
              sqx3 = e.getX();
              sqy3 = e.getY();
              repaint();// 再描画
              break;
            }
            if(sqnum2 > sqrcnt && sqrcnt==3){
              sqrflg=true;//mouseMovedの処理をしているときに立つフラグ
              //多角形の４点目のxy座標を取得
              sqx4 = e.getX();
              sqy4 = e.getY();
              repaint();// 再描画
              break;
            }
            if(sqnum2 > sqrcnt && sqrcnt==4){
              sqrflg=true;//mouseMovedの処理をしているときに立つフラグ
              //多角形の５点目のxy座標を取得
              sqx5 = e.getX();
              sqy5 = e.getY();
              repaint();// 再描画
              break;
            }
            if(sqnum2 > sqrcnt && sqrcnt==5){
              sqrflg=true;//mouseMovedの処理をしているときに立つフラグ
              //多角形の６点目のxy座標を取得
              sqx6 = e.getX();
              sqy6 = e.getY();
              repaint();// 再描画
              break;
            }
            break;
        case 17:
            if(crvcnt==2){
              crvflg=true;//mouseMovedの処理をしているときに立つフラグ
              //終点のxy座標の取得
              enx = e.getX();
              eny = e.getY();
              repaint();// 再描画
              break;
            }
      }
    }
}
