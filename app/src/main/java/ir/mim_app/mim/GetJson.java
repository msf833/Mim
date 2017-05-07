package ir.mim_app.mim;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import ir.mim_app.mim.assistClasses.studentAttributes;

/**
 * Created by MSF on 4/9/2017.
 */

public class GetJson extends AsyncTask<String ,Void, String> {

    String StrURl;
    public   String finalJson;
    boolean Recieved = false;
    JSONObject jsonobj;

    public GetJson(String strURl) {
        this.StrURl = strURl;

    }





    public String getStrURl() {
        return StrURl;
    }

    public void setStrURl(String strURl) {
        StrURl = strURl;
    }

    public void setFinalJson(String finalJson) {
        this.finalJson = finalJson;
    }

    public String getFinalJson() {
        return finalJson;
    }

    @Override
    protected String doInBackground(String... params) {
        String method= params[0];



        if(method.equals("courseList")){

            String name = params[1];
            String email = params[2];
            String contact = params[3];
            String password = params[4];

            try {
                URL url= new URL(StrURl);
//                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestProperty( "charset", "utf-8");
//                OutputStream os= httpURLConnection.getOutputStream();
//
//                BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestProperty("charset", "utf-8");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");

                String data= URLEncoder.encode("phoneNumber", "UTF-8")+"="+ URLEncoder.encode("محمحد","UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode("3319","UTF-8")+"&"+
                        URLEncoder.encode("Utype","UTF-8")+"="+URLEncoder.encode("2","UTF-8")+"&"+
                        URLEncoder.encode("ali","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

                wr.write( data );
                wr.flush();
                wr.close();

                //Charset.forName("UTF-8").encode(data);
//                bufferedWriter.write(data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                os.close();
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);

                }
                finalJson = sb.toString();
                Recieved = true;
                try {
                    jsonobj = new JSONObject(finalJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return sb.toString();
//                InputStream IS= httpURLConnection.getInputStream();
//                IS.close();
                // return "Registration success";

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        if(method.equals("CourseToProf")){

            String courseID = params[1];

            try {
                URL url= new URL(StrURl);
//                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestProperty( "charset", "utf-8");
//                OutputStream os= httpURLConnection.getOutputStream();
//
//                BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestProperty("charset", "utf-8");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");

                String data= URLEncoder.encode("courseID", "UTF-8")+"="+ URLEncoder.encode(courseID,"UTF-8")+"&";

                wr.write( data );
                wr.flush();
                wr.close();


                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);

                }
                finalJson = sb.toString();
                Recieved = true;
                try {
                    jsonobj = new JSONObject(finalJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return sb.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }





        if(method.equals("sendClassSch")){

            String proffID = params[1];
            String coursID = params[2];

            String coursdate = params[3];
            String courstime = params[4];
            String comments = params[5];

            String studentID = studentAttributes.studentID;


            try {
                URL url= new URL(StrURl);


                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestProperty("charset", "utf-8");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");

          String data = URLEncoder.encode("proffID", "UTF-8")+"="+ URLEncoder.encode(proffID,"UTF-8")+"&"+
                        URLEncoder.encode("coursID","UTF-8")+"=" + URLEncoder.encode(coursID,"UTF-8")+"&"+
                        URLEncoder.encode("studentID","UTF-8")+"="+URLEncoder.encode(studentID,"UTF-8")+"&"+
                        URLEncoder.encode("coursdate","UTF-8")+"="+URLEncoder.encode(coursdate,"UTF-8")+"&"+
                        URLEncoder.encode("courstime","UTF-8")+"="+URLEncoder.encode(courstime,"UTF-8")+"&"+
                        URLEncoder.encode("comments","UTF-8")+"="+ URLEncoder.encode(comments,"UTF-8");

                wr.write( data );
                wr.flush();
                wr.close();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);

                }
                finalJson = sb.toString();
                Recieved = true;
                try {
                    jsonobj = new JSONObject(finalJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return sb.toString();
//                InputStream IS= httpURLConnection.getInputStream();
//                IS.close();
                // return "Registration success";

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(method.equals("listSearch")){

            String query = params[1];



            String studentID = studentAttributes.studentID;
            try {
                URL url= new URL(StrURl);


                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestProperty("charset", "utf-8");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");

                String data = URLEncoder.encode("query", "UTF-8")+"="+ URLEncoder.encode(query,"UTF-8");

                wr.write( data );
                wr.flush();
                wr.close();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);

                }
                finalJson = sb.toString();
                Recieved = true;
                try {
                    jsonobj = new JSONObject(finalJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return sb.toString();
//                InputStream IS= httpURLConnection.getInputStream();
//                IS.close();
                // return "Registration success";

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(method.equals("proffresume")){

            String proffID = params[1];



            String studentID = studentAttributes.studentID;
            try {
                URL url= new URL(StrURl);


                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestProperty("charset", "utf-8");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");

                String data = URLEncoder.encode("proffID", "UTF-8")+"="+ URLEncoder.encode(proffID,"UTF-8");

                wr.write( data );
                wr.flush();
                wr.close();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);

                }
                finalJson = sb.toString();
                Recieved = true;
                try {
                    jsonobj = new JSONObject(finalJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return sb.toString();
//                InputStream IS= httpURLConnection.getInputStream();
//                IS.close();
                // return "Registration success";

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        if(method.equals("eventget")){

            String ID = params[1];



            String studentID = studentAttributes.studentID;
            try {
                URL url= new URL(StrURl);


                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestProperty("charset", "utf-8");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");

                String data = URLEncoder.encode("ID", "UTF-8")+"="+ URLEncoder.encode(ID,"UTF-8");

                wr.write( data );
                wr.flush();
                wr.close();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);

                }
                finalJson = sb.toString();
                Recieved = true;
                try {
                    jsonobj = new JSONObject(finalJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return sb.toString();
//                InputStream IS= httpURLConnection.getInputStream();
//                IS.close();
                // return "Registration success";

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(method.equals("listViewSearch")){

            String query = params[1];

            try {
                URL url= new URL(StrURl);

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestProperty("charset", "utf-8");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");

                String data = URLEncoder.encode("queryString", "UTF-8")+"="+ URLEncoder.encode(query,"UTF-8");

                wr.write( data );
                wr.flush();
                wr.close();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);

                }
                finalJson = sb.toString();
                Recieved = true;
                try {
                    jsonobj = new JSONObject(finalJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return sb.toString();
//                InputStream IS= httpURLConnection.getInputStream();
//                IS.close();
                // return "Registration success";

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(method.equals("signupReq")){

            String query = params[1];

            try {
                URL url= new URL(StrURl);

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestProperty("charset", "utf-8");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");

                String data = URLEncoder.encode("queryString", "UTF-8")+"="+ URLEncoder.encode(query,"UTF-8");

                wr.write( data );
                wr.flush();
                wr.close();


                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);

                }
                finalJson = sb.toString();
                Recieved = true;
                try {
                    jsonobj = new JSONObject(finalJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return sb.toString();
//                InputStream IS= httpURLConnection.getInputStream();
//                IS.close();
                // return "Registration success";

            } catch (IOException e) {
                e.printStackTrace();
            }
//                InputStream IS= httpURLConnection.getInputStream();
//                IS.close();
                // return "Registration success";

        }

        //signupRequest

        if(method.equals("signupRequest")){

            String phoneNumber = params[1];
            String password = params[2];
            String name = params[3];
            String family = params[4];

            try {
                URL url= new URL(StrURl);

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestProperty("charset", "utf-8");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");

                String data =
                        URLEncoder.encode("phoneNum", "UTF-8")+"="+ URLEncoder.encode(phoneNumber,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                        URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("family","UTF-8")+"="+URLEncoder.encode(family,"UTF-8");

                wr.write( data );
                wr.flush();
                wr.close();


                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);

                }
                finalJson = sb.toString();
                Recieved = true;
                try {
                    jsonobj = new JSONObject(finalJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return sb.toString();
//                InputStream IS= httpURLConnection.getInputStream();
//                IS.close();
                // return "Registration success";

            } catch (IOException e) {
                e.printStackTrace();
            }
//                InputStream IS= httpURLConnection.getInputStream();
//                IS.close();
            // return "Registration success";

        }

        return null;
    }


}