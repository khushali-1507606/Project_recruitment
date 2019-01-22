package com.admin.nidhi_khushali;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.CountDownTimer;

import com.example.win_10.newapp.NewNavigation;
import com.example.win_10.newapp.R;
import com.util.Util;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

public class Quiz_activity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Camera camera;
    int cameraCount = 0;
    Bitmap userImage;

    CountDownTimer ct;
    int count=0;
    // TextView qno;
    TextView que;
    TextView txtTimer;
    TextView txtSubmitTest;
    RadioButton rbCh1;
    RadioButton rbCh2;
    RadioButton rbCh3;
    RadioButton rbCh4;
    RadioGroup rbGroup;
    Button btnNextQue;
    Button btnPrevQue;
    Button btnClearAns;

    Quiz_question obQuizQue;

    int qNo=0;
    double score=0.0;
    int queSubmittedNo=0;
    String arrAnswers[];//marked ans
    int flagAns[];

    ArrayList<String> listQueId, listQue, listOption1, listOption2, listOption3, listOption4, listAnswer;
    ArrayList<Integer> listMarks;
    ArrayList<Double> listNegativeMarks;

    String advt="Adv1", candId="1001", testId="Test1", quesId="1", selectOpt="Hyper Text Markup Language";

    int time=30;
    //String testId="Test1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_quiz);
        Log.d("log1 : ","log 1");
        //Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

        Intent rcv=getIntent();
        String t=rcv.getStringExtra("time");
        time=Integer.parseInt(t);

        listQueId=new ArrayList<>();
        listQue=new ArrayList<>();
        listOption1=new ArrayList<>();
        listOption2=new ArrayList<>();
        listOption3=new ArrayList<>();
        listOption4=new ArrayList<>();
        listAnswer=new ArrayList<>();
        listMarks=new ArrayList<>();
        listNegativeMarks=new ArrayList<>();
        initViews();

        getSelectedQuestions getQueObj=new getSelectedQuestions();
        getQueObj.execute();


        rbCh1.setOnCheckedChangeListener(this);
        rbCh2.setOnCheckedChangeListener(this);
        rbCh3.setOnCheckedChangeListener(this);
        rbCh4.setOnCheckedChangeListener(this);

        btnNextQue.setOnClickListener(this);
        btnPrevQue.setOnClickListener(this);
        btnClearAns.setOnClickListener(this);

        txtSubmitTest.setOnClickListener(this);

        // Next_question(qNo);
        // Next_question();

        start_timer(time);

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();    //Information about a camera
        cameraCount = Camera.getNumberOfCameras();//Returns the number of physical cameras available on this device.

        //Open the Camera Object

        /*
         *  As Android's own Camera application does, the recommended way to access the camera is to open Camera on a
         *  separate thread that's launched from onCreate().
         *  This approach is a good idea since it can take a while and might bog down the UI thread.
         *  In a more basic implementation, opening the camera can be deferred to the onResume() method to facilitate code reuse and keep the flow of control simple.
         */

        for (int camIdx = 0; camIdx<cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx,cameraInfo);//Returns the information about a particular camera.
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    camera = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    Log.e("Camera Info", "Camera failed to open: " + e.toString());
                }


            }
        }


        // camera = Camera.open();
        SurfaceView view = new SurfaceView(this);
        /*
        Taking a picture usually requires that your users see a preview of their subject before clicking the shutter. To do so, you can use a SurfaceView to draw previews of what the camera sensor is picking up.*/

        try {
            camera.setPreviewDisplay(view.getHolder());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        camera.startPreview();
    }
    Camera.PictureCallback jpegCallBack=new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            userImage = BitmapFactory.decodeByteArray(data, 0, data.length);
            // set compress format quality and stream
            BackTask backTask=new BackTask();
            backTask.execute();
            camera.startPreview();




        }
    };

    public void initViews(){
        // qno=findViewById(R.id.textViewQNo);
        que=findViewById(R.id.textViewQue);
        txtTimer=findViewById(R.id.textViewCountDownTimer);
        txtSubmitTest=findViewById(R.id.textViewSubmitTest);

        rbCh1=findViewById(R.id.radioOption1);
        rbCh2=findViewById(R.id.radioOption2);
        rbCh3=findViewById(R.id.radioOption3);
        rbCh4=findViewById(R.id.radioOption4);
        btnNextQue=findViewById(R.id.buttonNextQue);
        btnPrevQue=findViewById(R.id.buttonPrevQue);
        btnClearAns=findViewById(R.id.buttonClearAns);



        obQuizQue = new Quiz_question();

        int len=100;//listQue.size();

        arrAnswers= new String[len];
        flagAns= new int[len];

        for(int i=0;i<len;i++){
            flagAns[i]=0;
            arrAnswers[i]="";
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
       // finish();
        if (fin==0){
        submitTest();
    }}

    @Override
    protected void onStop() {
        super.onStop();

        if (fin==0){
            submitTest();

        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fin==0){
            submitTest();
        }
//        submitTest();
    }

    public void start_timer(int time){

      ct= new CountDownTimer(time*60000, 1000) {

            @Override
            public void onTick(long l) {
                long hrs=l/(1000*60*60);
                long min=l/(1000*60)- (hrs*60);
                long sec=l/(1000)- (min*60+3600);

                txtTimer.setText(" Time: "+" "+hrs+" : "+min+" : "+sec+" ");
                if (l/1000%5==0) {
                    count++;
                    camera.takePicture(null, null, null, jpegCallBack);
                  //  Toast.makeText(Quiz_activity.this, " "+count, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFinish() {
                txtTimer.setText("Finished");
                submitTest();
            }


        }.start();

    }

    // public void Next_question(int a) {
    public void Next_question(int qNo) {

        // handler.sendEmptyMessageDelayed(101,1000);
        getNextQue(qNo,listQue,listOption1,listOption2,listOption3,listOption4);

    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==101){
            }
        }

    };


    public void getNextQue(int a, ArrayList listQue, ArrayList listOption1, ArrayList listOption2, ArrayList listOption3, ArrayList listOption4 )
    {


        if (listOption1.get(a).equals(arrAnswers[a])){
            rbCh1.setChecked(true);
        }
        else if (listOption2.get(a).equals(arrAnswers[a])){
            rbCh2.setChecked(true);
        }
        else if (listOption3.get(a).equals(arrAnswers[a])){
            rbCh3.setChecked(true);
        }
        else if (listOption4.get(a).equals(arrAnswers[a])){
            rbCh4.setChecked(true);
        }
        else if (arrAnswers[a].equals("")){
            uncheckAll(a);
        }
        else{

        }

        que.setText("Q.no "+(1+a)+":"+listQue.get(a));
        rbCh1.setText(listOption1.get(a).toString());
        rbCh2.setText(listOption2.get(a).toString());
        rbCh3.setText(listOption3.get(a).toString());
        rbCh4.setText(listOption4.get(a).toString());
        handler.sendEmptyMessageDelayed(101,2000);


//        rbCh1.setChecked(false);
//        rbCh2.setChecked(false);
//        rbCh3.setChecked(false);
//        rbCh4.setChecked(false);

//        if(obQuizQue.getChoice1(a).equals(arrAnswers[a])){
//            rbCh1.setChecked(true);
//        }
        //Log.d("listQue: ",listQue.size()+"..."+listQue.get(a));
        // Toast.makeText(this, "a: "+a+"listOption1.get(a): "+listOption1.get(a)+" arrAns[a]: "+arrAnswers[a], Toast.LENGTH_SHORT).show();
//
//       if (listOption1.get(a).equals(arrAnswers[a])){
//           rbCh1.setChecked(true);
//       }
//       else if (listOption2.get(a).equals(arrAnswers[a])){
//           rbCh2.setChecked(true);
//       }
//       else if (listOption3.get(a).equals(arrAnswers[a])){
//           rbCh3.setChecked(true);
//       }
//       else if (listOption4.get(a).equals(arrAnswers[a])){
//           rbCh4.setChecked(true);
//       }
//       else if (arrAnswers[a].equals("")){
//           uncheckAll(a);
//       }
//       else{
//
//       }

    }

    public void uncheckAll(int a){
        rbCh1.setChecked(false);
        rbCh2.setChecked(false);
        rbCh3.setChecked(false);
        rbCh4.setChecked(false);

        flagAns[a]=0;
        arrAnswers[a]="";
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();


        if(id==R.id.buttonNextQue) {

            if (qNo < listQue.size()) {

                //store answers...
                if(rbCh1.isChecked()){
                    arrAnswers[qNo]=rbCh1.getText().toString();
                }
                else if(rbCh2.isChecked()){
                    arrAnswers[qNo]=rbCh2.getText().toString();

                }
                else if(rbCh3.isChecked()){
                    arrAnswers[qNo]=rbCh3.getText().toString();

                }
                else if(rbCh4.isChecked()){
                    arrAnswers[qNo]=rbCh4.getText().toString();
                }
                else{
                    arrAnswers[qNo]="";
                }

                //get next question...
                qNo++;
                if (qNo < listQue.size()) {
                    Next_question(qNo);
                }

                else {
                    Toast.makeText(this, "Finished!", Toast.LENGTH_SHORT).show();
                }
            }

            else{
                //Toast.makeText(this, "Thanks...", Toast.LENGTH_SHORT).show();
            }

        }


        else if(id==R.id.buttonPrevQue){

            if(qNo>0 && qNo<listQue.size()){
                //store answers...
                if(rbCh1.isChecked()){
                    arrAnswers[qNo]=rbCh1.getText().toString();
                }
                else if(rbCh2.isChecked()){
                    arrAnswers[qNo]=rbCh2.getText().toString();

                }
                else if(rbCh3.isChecked()){
                    arrAnswers[qNo]=rbCh3.getText().toString();

                }
                else if(rbCh4.isChecked()){
                    arrAnswers[qNo]=rbCh4.getText().toString();
                }
                else{
                    arrAnswers[qNo]="";
                }

                //get prev question...
                qNo--;
                Next_question(qNo);
            }

            else{

                if(qNo>=listQue.size())
                {

                    //set qno=last que
                    qNo=listQue.size()-1;

                    if(rbCh1.isChecked()){
                        arrAnswers[qNo]=rbCh1.getText().toString();
                    }
                    else if(rbCh2.isChecked()){
                        arrAnswers[qNo]=rbCh2.getText().toString();

                    }
                    else if(rbCh3.isChecked()){
                        arrAnswers[qNo]=rbCh3.getText().toString();

                    }
                    else if(rbCh4.isChecked()){
                        arrAnswers[qNo]=rbCh4.getText().toString();
                    }
                    else{
                        arrAnswers[qNo]="";
                    }

                    //switch from last que to 2nd last...
                    qNo=listQue.size()-2;
                    Next_question(qNo);
                }

                //when there are no more prev que...
                else{
                    //Toast.makeText(this, "no more prev que", Toast.LENGTH_SHORT).show();
                }

            }

        }

        else if(id==R.id.buttonClearAns){
            uncheckAll(qNo);
        }

        else if(id==R.id.textViewSubmitTest){

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Submit Test");
            builder.setMessage("Once you submit the test you won't be able to make any change.Are yu sure you want to submit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    finish();
                    submitTest();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog submitBox=builder.create();
            submitBox.show();
            //******28sep
            /*if(qNo>=listQue.size()) {
                //set qno=last que
                qNo = listQue.size() - 1;
            }
            else{
                qNo=qNo;
            }
            if(rbCh1.isChecked()){
                arrAnswers[qNo]=rbCh1.getText().toString();
            }
            else if(rbCh2.isChecked()){
                arrAnswers[qNo]=rbCh2.getText().toString();

            }
            else if(rbCh3.isChecked()){
                arrAnswers[qNo]=rbCh3.getText().toString();

            }
            else if(rbCh4.isChecked()){
                arrAnswers[qNo]=rbCh4.getText().toString();
            }
            else{
                arrAnswers[qNo]=null;
            }
            //******28sep
            score=0;

            for(int i = 0; i < listQue.size(); i++) {


            }
            for(int i = 0; i < listQue.size(); i++) {

                if (listAnswer.get(i).equals(arrAnswers[i])) {
                    flagAns[i] = 1;
                }
                else {
                    flagAns[i] = 0;
                }
            }
            //count score...
            for (int i = 0; i < listQue.size(); i++) {
                if (flagAns[i] == 1) {
                    score=score+listMarks.get(i);
                }
                else{
                    score=score-listNegativeMarks.get(i);
                }
            }

            Toast.makeText(this, "Your Score: " + score, Toast.LENGTH_SHORT).show();*/
        }

    }

    //function to submit the test
    public void submitTest(){
        //******28sep
        if(qNo>=listQue.size()) {
            //set qno=last que
            qNo = listQue.size() - 1;
        }
        else{
            qNo=qNo;
        }
        if(rbCh1.isChecked()){
            arrAnswers[qNo]=rbCh1.getText().toString();
        }
        else if(rbCh2.isChecked()){
            arrAnswers[qNo]=rbCh2.getText().toString();

        }
        else if(rbCh3.isChecked()){
            arrAnswers[qNo]=rbCh3.getText().toString();

        }
        else if(rbCh4.isChecked()){
            arrAnswers[qNo]=rbCh4.getText().toString();
        }
        else{
            arrAnswers[qNo]="";
        }
        //******28sep
        score=0.0;
        for(int i = 0; i < listQue.size(); i++) {


        }
        for(int i = 0; i < listQue.size(); i++) {

            if (listAnswer.get(i).equals(arrAnswers[i])) {
                flagAns[i] = 1;
            }
            else {
                flagAns[i] = 0;
            }
        }
        //count score...
//        for (int i = 0; i < listQue.size(); i++) {
//            if (flagAns[i] == 1) {
//                //Toast.makeText(this, "..."+listMarks.get(i), Toast.LENGTH_LONG).show();
//                score=score+listMarks.get(i);
//            }
//            else{
//                score=score-listNegativeMarks.get(i);
//            }
//        }
        for (int i=1;i<=listQue.size();i++) {
            insertUserExamData insertObj = new insertUserExamData(advt,candId,testId,String.valueOf(i),arrAnswers[i-1]);
            insertObj.execute();
        }

        ct.cancel();

//        Toast.makeText(this, "*****"+queSubmittedNo, Toast.LENGTH_SHORT).show();
//        if (queSubmittedNo==listQue.size()-1){
//            showalert("Test Submitted Successfully!");
//        }
//        else {
//            showalert("Error Occurred!");
//        }
       // Toast.makeText(this, "Your Score: " + score, Toast.LENGTH_SHORT).show();            ct.cancel();
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        /*String checkedCh=compoundButton.getText().toString();

        //Toast.makeText(this, "you selected "+checkedCh, Toast.LENGTH_SHORT).show();
        arrAnswers[qNo]=checkedCh;
        for(int i=0;i<arrAnswers.length;i++){
            Toast.makeText(this, "arrAnswer["+i+"] = "+arrAnswers[i], Toast.LENGTH_SHORT).show();*/

    }

    class getSelectedQuestions extends AsyncTask {

        ProgressDialog pd;
        String response;
        @Override
        protected void onPreExecute() {
            //Toast.makeText(Quiz_activity.this, "preexecute", Toast.LENGTH_SHORT).show();
            pd=new ProgressDialog(Quiz_activity.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pd.setTitle("Fetching Questions");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJSON();
            return null;
        }

        public  String getJSON(){
            StringBuilder sb=new StringBuilder();
            try {
                //String url="http://192.168.42.16/AdvertisementsFolder/getAllAdds.php";
                String url=Util.url_GetSelectedQuestions;
                String data="";
                URL x=new URL(url);
                HttpURLConnection connection=(HttpURLConnection)x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data=URLEncoder.encode("testId","UTF-8")+ "="+ URLEncoder.encode("Test1","UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                //fetching response
                InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());
                Scanner scanner=new Scanner(inputStreamReader);
                while(scanner.hasNext()){
                    sb.append(scanner.nextLine());
                }

                return sb.toString();

            }

            catch (Exception e) {
                //Toast.makeText(AppFillForm.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(Object o) {
            //Toast.makeText(Quiz_activity.this, "onPostExecute,response: "+response, Toast.LENGTH_SHORT).show();
            Log.d("response: ",response);
            super.onPostExecute(o);
            pd.dismiss();
            try {
                JSONArray jsonArray=new JSONArray(response);
                Log.d("json len: ",jsonArray.length()+"");

                //Toast.makeText(CandidateDetails.this, "JSON length:"+jsonArray.length(), Toast.LENGTH_SHORT).show();
                if (jsonArray.length()>0){
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject job=jsonArray.getJSONObject(i);

                        String queId=job.getString("QuestionId");
                        listQueId.add(queId);
                        listQue.add(job.getString("Question"));
                        listOption1.add(job.getString("Option1"));
                        listOption2.add(job.getString("Option2"));
                        listOption3.add(job.getString("Option3"));
                        listOption4.add(job.getString("Option4"));
                        listAnswer.add(job.getString("QuestionAnswer"));
                        String marks=job.getString("MarksOfQuestion");
                        //listMarks.add(Integer.parseInt(marks));
                        String negMarks=job.getString("NegativeMarking");
                        //listNegativeMarks.add(Double.parseDouble(negMarks));


                    }
                   // Toast.makeText(Quiz_activity.this, "listQue:"+listQue.size(), Toast.LENGTH_LONG).show();
                    getNextQue(qNo,listQue,listOption1,listOption2,listOption3,listOption4);
                }
                else{
                    Toast.makeText(Quiz_activity.this, "Questions not Available", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
               // Toast.makeText(Quiz_activity.this, "Oops!catch executed..."+e.getMessage(), Toast.LENGTH_SHORT).show();
                for (int i=0;i<obQuizQue.questions.length;i++){
                    listQueId.add(String.valueOf(i));
                    listQue.add(obQuizQue.getQuestion(i));
                    listOption1.add(obQuizQue.getChoice1(i));
                    listOption2.add(obQuizQue.getChoice2(i));
                    listOption3.add(obQuizQue.getChoice3(i));
                    listOption4.add(obQuizQue.getChoice4(i));
                    listAnswer.add(obQuizQue.getAnswer(i));
                }
                getNextQue(qNo,listQue,listOption1,listOption2,listOption3,listOption4);
                e.printStackTrace();
            }

        }
    }

    String res;
    //ToDo
    class insertUserExamData extends AsyncTask {

        String adv="",CId="",TId="",QId="",Opt="";
        insertUserExamData(String advt, String candId, String testId, String quesId, String selectOpt){

            adv = advt;
            CId=candId;
            TId=testId;
            QId=quesId;
            Opt=selectOpt;
        }
        ProgressDialog pd;
        String response;
        @Override
        protected void onPreExecute() {
            //Toast.makeText(Quiz_activity.this, "preexecute", Toast.LENGTH_SHORT).show();
            pd=new ProgressDialog(Quiz_activity.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJSON();
            return null;
        }

        public  String getJSON(){
            StringBuilder sb=new StringBuilder();
            try {
                //String url="http://192.168.42.16/AdvertisementsFolder/getAllAdds.php";
                String url=Util.url_InsertUserExamData;
                String data="";
                URL x=new URL(url);
                HttpURLConnection connection=(HttpURLConnection)x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                data = URLEncoder.encode("advt", "UTF-8") + "=" + URLEncoder.encode(adv, "UTF-8") + "&" +
                        URLEncoder.encode("candId", "UTF-8") + "=" + URLEncoder.encode(CId, "UTF-8") + "&" +
                        URLEncoder.encode("testId", "UTF-8") + "=" + URLEncoder.encode(TId, "UTF-8") + "&" +
                        URLEncoder.encode("quesId", "UTF-8") + "=" + URLEncoder.encode(QId, "UTF-8") + "&" +
                        URLEncoder.encode("selectOpt", "UTF-8") + "=" + URLEncoder.encode(Opt, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();

                bufferedWriter.close();

                //fetching response
                InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());
                Scanner scanner=new Scanner(inputStreamReader);
                while(scanner.hasNext()){
                    sb.append(scanner.nextLine());
                }

                return sb.toString();

            }

            catch (Exception e) {
                //Toast.makeText(AppFillForm.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(Object o) {
            //Toast.makeText(Quiz_activity.this, "onPostExecute,response "+QId+": "+response, Toast.LENGTH_SHORT).show();
            Log.d("response: ",response);
            super.onPostExecute(o);
            pd.dismiss();
            try {
                JSONObject job=new JSONObject(response);
                res=job.getString("code");

                if (res.trim().equals("success")){
                    //Toast.makeText(Quiz_activity.this, "Test Submitted", Toast.LENGTH_SHORT).show();
                    queSubmittedNo++;
                    //Toast.makeText(Quiz_activity.this, " "+queSubmittedNo, Toast.LENGTH_SHORT).show();

                    if (queSubmittedNo==listQue.size()){
                        showalert("Test submitted successfully");
                    }
                }

                else{
                    Toast.makeText(Quiz_activity.this, "Submission Unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }
            catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(Quiz_activity.this, "Oops!catch executed..."+e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }
    }

    int fin=0;
    public void showalert(String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Aptitude Test");
        builder.setMessage(msg);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //startActivity(new Intent(Quiz_activity.this,NewNavigation.class));
               // finish();
                if (msg.equals("Test submitted successfully")){
                    fin=1;
                    finish();
                }
            }
        });
        AlertDialog showmsg=builder.create();
        showmsg.show();
    }

    private class BackTask extends AsyncTask<Void,Void,String> {
        TextView tv;
        protected void onPreExecute(){
          //  Toast.makeText(Quiz_activity.this, "Uploading image", Toast.LENGTH_SHORT).show();
        }
        protected String doInBackground(Void...params){
            String text="";
//we need to convert image bitmap into string
            try{
                // Create HttpClitent instance
                HttpClient httpClient = new DefaultHttpClient();//used as a wrapper to send data
                // Create HttpPost instance with specifying php file on server to handle file uploading
              //  HttpPost httpPostRequest = new HttpPost("http://202.164.39.172:2345/test/Prsc_recruitment/img_upd.php");
                HttpPost httpPostRequest = new HttpPost("http://202.164.39.172:2345/test/Prsc_recruitment/img_upd.php");
                // Read image file and convert it to byte array
                //    Bitmap bitmap=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/Rose.jpg");
                ByteArrayOutputStream bo=new ByteArrayOutputStream();
                userImage.compress(Bitmap.CompressFormat.JPEG, 90, bo);// we compress the image into png format and then passed and set the quality to 100% and 3rd parameter is output stream
                byte[] data=bo.toByteArray();
                // Specify HttpClient parameters
                httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));
                // Create InputStreamBody instance with specifying image data destination file name
                InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), "/index_files/"+candId+"_"+count+".jpg");
                Log.d("i11",candId+count+"");
                // Create MultipartEntityBuilder instance
                MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
                // Set multi-part mode
                multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                // Attach image data in InputStreamBody instance to MultipartEntityBuilder instance
                multipartEntity.addPart("img_file", inputStreamBody);
                multipartEntity.addTextBody("path",candId);
                // Set MultipartEntityBuilder instance to HttpPost
                httpPostRequest.setEntity(multipartEntity.build());

                // Start uploading the image and get the result from remote server

                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                text=httpClient.execute(httpPostRequest, responseHandler);
                return text;
            }catch(Exception e){e.printStackTrace();}
            return null;
        }
        protected void onPostExecute(String result){

          //  Toast.makeText(Quiz_activity.this, "Result "+result, Toast.LENGTH_SHORT).show();
            if(result.contains("ok"))
Log.i("image ","uploaded");
            else
                Log.i("image ","unsuccessful");

        }
    }

}