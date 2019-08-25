/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mycompany.money.SendMail;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author AKASH
 */
public class login extends HttpServlet {

    static ResultSet rs = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MessagingException, GeneralSecurityException {
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            String uname = request.getParameter("username");
            String pwd = request.getParameter("password");
            String loguname = request.getParameter("logusername");
            String logpwd = request.getParameter("logpassword");
            String log = request.getParameter("loggedin");
            String loggedin = request.getParameter("login");
            String sign = request.getParameter("submission");
            String email = request.getParameter("mailid");
            String amountgiven = request.getParameter("amountgiven");
            String usname = request.getParameter("usname");
            String rsname = request.getParameter("rsname");
            String oper = request.getParameter("oper");
            MyDb db = new MyDb();
            Connection con = db.getCon();
            Statement stmt = con.createStatement();
            if (log != null) {
                if((!uname.equals(""))&&(!pwd.equals(""))&&(!email.equals(""))){
                    stmt.executeUpdate("insert into validation (username,password,amount,email) values('" + uname + "','" + pwd + "',5000,'" + email + "')");
                    response.sendRedirect("calc.html");
                }
                else
                {
                    response.sendRedirect("calc3.html");
                }
            } else if (sign != null) {
                response.sendRedirect("log.html");
            } else if (loggedin != null) {
                String searchQuery = "select * from validation where username='" + loguname + "' AND password='" + logpwd + "'";
                rs = stmt.executeQuery(searchQuery);
                boolean more = rs.next();
                if (!more) {
                    response.sendRedirect("calc2.html");
                } else if (more) {
                    out.println("<html>\n"
                            + "    <head>\n"
                            + "        <link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" rel=\"stylesheet\">\n"
                            + "        <script src=\"send.js\">\n"
                            + "            </script>\n"
                            + "            \n"
                            + "        \n"
                            + "        <style>            \n"
                            + "            td{\n"
                            + "                padding: 10px;\n"
                            + "            }\n"
                            + "            div{\n"
                            + "                width: 30%;\n"
                            + "                border: 1px solid black;\n"
                            + "                border-radius: 5px;\n"
                            + "                background-color: lightseagreen;\n"
                            + "                margin-top: 100px;\n"
                            + "            }\n"
                            + "        </style>\n"
                            + "    </head>\n"
                            + "    <body onload=\"getname()\">\n"
                            + "        <center>\n"
                            + "            <div align=\"center\" id=\"demo\">\n"
                            + "                <form>\n"
                            + "                    <table id=\"table\">\n"
                            + "                        <tr>\n"
                            + "                            <td>Sender Name:</td>\n"
                            + "                            <td>" + loguname + "</td>\n"
                            + "                        </tr>\n"
                            + "                        <tr>\n"
                            + "                            <td>Receiver Name:</td>\n"
                            + "                            <td><input type=\"text\" class=\"form-control\" size=\"18\" maxlength=15 id=\"rsname\" value=\"\"></td>\n"
                            + "                        </tr>\n"
                            + "                        <tr>\n"
                            + "                            <td>Enter Amount:</td>\n"
                            + "                            <td><input type=\"text\" class=\"form-control\" size=\"18\" maxlength=15 id=\"amountgiven\" value=\"\"></td>\n"
                            + "                        </tr>\n"
                            + "                        <tr>\n"
                            + "                            <td colspan=\"2\" style=\"text-align: center\">\n"
                            + "                                <input type=\"button\" onclick=\"sendotp();\" class=\"btn btn-success\"  value=\"sendotp\">\n"
                            + "                                <input type=\"button\" onclick=\"verifyotp();\" class=\"btn btn-success\"  value=\"verifyotp\">\n"
                            + "                            </td>\n"
                            + "                        </tr>\n"
                            + "                        <tr>\n"
                            + "                            <td>Enter OTP:</td>\n"
                            + "                            <td><input type=\"text\" class=\"form-control\" size=\"18\" maxlength=15 id=\"otp\" value=\"\"></td>\n"
                            + "                        </tr>\n"  
                            + "                        <tr>\n"
                            + "                            <td colspan=\"2\" style=\"text-align: center\">\n"
                            + "                                <input type=\"button\" disabled  onclick=\"transfer();\" class=\"btn btn-success\" id=\"able\" value=\"transfer\">\n"
                            + "                                <input type=\"button\" disabled  onclick=\"deposit();\"  class=\"btn btn-success\" id=\"able2\" value=\"deposit\">\n"
                            + "                            </td>\n"
                            + "                        </tr> \n"
                            + "                        <tr>\n"
                            + "                            <td colspan=\"2\" style=\"text-align: center\">\n"
                            + "                                <input type=\"button\" onclick=\"transdtl();\" class=\"btn btn-success\" value=\"transdetail\">\n"
                            + "                            </td>\n"
                            + "                        </tr>\n"
                            + "                    </table>\n"
                            + "                </form>\n"
                            + "            </div>\n"
                            + "        </center>\n"
                            + "    </body>\n"
                            + "</html>\n"
                            + "");
                }

            }

            if ("transfer".equals(oper)) {
                ResultSet r;
                int total;
                String searchQuery2 = "select * from validation where username='" + rsname + "'";
                r = stmt.executeQuery(searchQuery2);
                if (r.next()) {
                    int amt = r.getInt("amount");
                    String searchQuery = "select amount from validation where username='" + usname + "'";//"select donatedamount from donation where receivername='"+ rsname + "'";
                    rs = stmt.executeQuery(searchQuery);
                    rs.next();
                    String i = rs.getString("amount");
                    int x = Integer.parseInt(i);
                    if (!amountgiven.equals("") && (!rsname.equals(usname))) {
                        int y = Integer.parseInt(amountgiven);
                        total = x - y;
                        if (total >= 5000) {
                            Date date = new Date();
                            long time = date.getTime();
                            Timestamp ts = new Timestamp(time);
                            stmt.executeUpdate("update validation set amount=" + total + " where username='" + usname + "'");
                            stmt.executeUpdate("update validation set amount=" + (amt + y) + " where username='" + rsname + "'");
                            stmt.executeUpdate("insert into donation values (" + y + " ,'" + rsname + "','" + usname + "','" + ts + "')");
                            out.println("Available Balance:" + total);
                        } else {
                            out.println("Insufficient Balance!!!Deposit Amount!!");
                        }

                    } else {
                        out.println("enter the amount stupid");
                    }
                } else {
                    out.println("Receiver not available!!!");
                }

            } else if ("deposit".equals(oper)) {
                String j;
                int totall;
                String searchQueryy = "select amount from validation where username='" + usname + "'";
                rs = stmt.executeQuery(searchQueryy);
                rs.next();
                j = rs.getString("amount");
                int a = Integer.parseInt(j);
                int b = Integer.parseInt(amountgiven);
                totall = a + b;
                stmt.executeUpdate("update validation set amount=" + totall + " where username='" + usname + "'");
                out.println("Available Balance:" + totall);
            } else if ("transdtl".equals(oper)) {
                rs = stmt.executeQuery("select receivername,donateddate,donatedamount from donation where donatorname='" + usname + "' order by donateddate desc");
                out.println("<html><body>");
                out.println("<table  border= 1px solid black border-radius= 5px margin-top: 100px>");
                out.println("<tr><th>Receiver</th><th>Date</th><th>Amount</th><tr>");
                while (rs.next()) {
                    String n = rs.getString("receivername");
                    String nm = rs.getString("donateddate");
                    int s = rs.getInt("donatedamount");
                    out.println("<tr><td>" + n + "</td><td>" + nm + "</td><td>" + s + "</td></tr>");
                }
                out.println("</table>");
                out.println("</html></body>");
            }
            else if ("sendotp".equals(oper)) {
                String[] mail=new String[1];
                String searchQueryy = "select email from validation where username='" + usname + "'";
                rs = stmt.executeQuery(searchQueryy);
                rs.next();
                mail[0] = rs.getString(1);
                SendMail.main(mail);
                out.print("OTP Sent");
            }
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (MessagingException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (MessagingException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}