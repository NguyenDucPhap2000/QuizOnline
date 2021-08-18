/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Context.DBContext;
import Entity.Account;
import Entity.Question;
import Entity.Role;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class QuizDAO {

    public List<Account> getAccount() throws Exception {
        //Open connection with sql
        Connection cnn = null;
        // move staement to sql server
        PreparedStatement ps = null;
        // get result from sql 
        ResultSet rs = null;
        DBContext db = DBContext.getInstance();
        List<Account> list = new ArrayList<>();
        try {
            String sql = "select * from [User]";
            cnn = db.getConnection();
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            // get account from sql and add to list
            while (rs.next()) {
                Account a = new Account(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("roleid"),
                        rs.getString("email"),
                        rs.getDate("created_date"));
                list.add(a);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(rs, ps, cnn);
        }
    }

    public void insertUser(String user, String pass, int role, String email, Date date) throws Exception {
        //Open connection with sql
        Connection cnn = null;
        // move staement to sql server
        PreparedStatement ps = null;
        // get result from sql 
        ResultSet rs = null;
        DBContext db = DBContext.getInstance();
        try {
            String sql = "Insert into [User](username,[password],roleid,email,"
                    + "created_date) values (?,?,?,?,?)";
            cnn = db.getConnection();
            ps = cnn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            ps.setInt(3, role);
            ps.setString(4, email);
            ps.setDate(5, date);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(rs, ps, cnn);
        }
    }

    public List<Role> getAllRole() throws Exception {
        //Open connection with sql
        Connection cnn = null;
        // move staement to sql server
        PreparedStatement ps = null;
        // get result from sql 
        ResultSet rs = null;
        DBContext db = DBContext.getInstance();
        List<Role> list = new ArrayList<>();
        try {
            String sql = "select * from [Role]";
            cnn = db.getConnection();
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            // get Role from sql and add to list
            while (rs.next()) {
                Role r = new Role(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("created_by"),
                        rs.getDate("created_date"));
                list.add(r);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(rs, ps, cnn);
        }
    }

    public int getRoleByUserPass(String username, String pass) throws Exception {
        //Open connection with sql
        Connection cnn = null;
        // move staement to sql server
        PreparedStatement ps = null;
        // get result from sql 
        ResultSet rs = null;
        DBContext db = DBContext.getInstance();
        try {
            String sql = "select roleid from [User] where username = ? and password = ?";
            cnn = db.getConnection();
            ps = cnn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            // get roleid from sql 
            int result = 0;
            while (rs.next()) {
                result = rs.getInt("roleid");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(rs, ps, cnn);
        }
    }

    public int checkDuplicateUser(String username) throws Exception {
        //Open connection with sql
        Connection cnn = null;
        // move staement to sql server
        PreparedStatement ps = null;
        // get result from sql 
        ResultSet rs = null;
        DBContext db = DBContext.getInstance();
        try {
            String sql = "select COUNT(*) from [User] where username = ?";
            cnn = db.getConnection();
            ps = cnn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            // get number of account from sql 
            int result = 0;
            while (rs.next()) {
                result = rs.getInt(1);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(rs, ps, cnn);
        }
    }

    public List<Question> getRandomQuestion(int number) throws Exception {
        //Open connection with sql
        Connection cnn = null;
        // move staement to sql server
        PreparedStatement ps = null;
        // get result from sql 
        ResultSet rs = null;
        DBContext db = DBContext.getInstance();
        List<Question> list = new ArrayList<>();
        try {
            String sql = "select TOP " + number + " * from Question order by NEWID()";
            cnn = db.getConnection();
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            // get question from sql and add to list
            while (rs.next()) {
                Question q = new Question(
                        rs.getInt("id"),
                        rs.getString("question"),
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getString("option4"),
                        rs.getString("answers"),
                        rs.getInt("user_id"),
                        rs.getString("created_by"),
                        rs.getDate("created_date")
                );
                list.add(q);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(rs, ps, cnn);
        }
    }

    public int countQuestion() throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = DBContext.getInstance();
        int count = 0;
        try {
            String sql = "select count(*) from Question";
            cnn = db.getConnection();
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(rs, ps, cnn);
        }
    }

    public List<Question> getQuestionByID(int id) throws Exception {
        //Open connection with sql
        Connection cnn = null;
        // move staement to sql server
        PreparedStatement ps = null;
        // get result from sql 
        ResultSet rs = null;
        DBContext db = DBContext.getInstance();
        List<Question> list = new ArrayList<>();
        try {
            String sql = "select * from Question where id = ?";
            cnn = db.getConnection();
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            // get question from sql and add to list
            while (rs.next()) {
                Question q = new Question(
                        rs.getInt("id"),
                        rs.getString("question"),
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getString("option4"),
                        rs.getString("answers"),
                        rs.getInt("user_id"),
                        rs.getString("created_by"),
                        rs.getDate("created_date")
                );
                list.add(q);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(rs, ps, cnn);
        }
    }

    public void insertQuestion(String Question, String op1, String op2, String op3, String op4, String answers, int userId, String userName, Date date) throws Exception {
        //Open connection with sql
        Connection cnn = null;
        // move staement to sql server
        PreparedStatement ps = null;
        // get result from sql 
        ResultSet rs = null;
        DBContext db = DBContext.getInstance();
        try {
            String sql = "insert into Question "
                    + "(question,option1,option2,option3,option4,answers,[user_id],created_by,created_date) \n"
                    + "values(?,?,?,?,?,?,?,?,?)";
            cnn = db.getConnection();
            ps = cnn.prepareStatement(sql);
            ps.setString(1, Question);
            ps.setString(2, op1);
            ps.setString(3, op2);
            ps.setString(4, op3);
            ps.setString(5, op4);
            ps.setString(6, answers);
            ps.setInt(7, userId);
            ps.setString(8, userName);
            ps.setDate(9, date);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(rs, ps, cnn);
        }
    }

    public List<Question> getAllQuestion() throws Exception {
        //Open connection with sql
        Connection cnn = null;
        // move staement to sql server
        PreparedStatement ps = null;
        // get result from sql 
        ResultSet rs = null;
        DBContext db = DBContext.getInstance();
        List<Question> list = new ArrayList<>();
        try {
            String sql = "select * from Question";
            cnn = db.getConnection();
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            // get question from sql and add to list
            while (rs.next()) {
                Question q = new Question(
                        rs.getInt("id"),
                        rs.getString("question"),
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getString("option4"),
                        rs.getString("answers"),
                        rs.getInt("user_id"),
                        rs.getString("created_by"),
                        rs.getDate("created_date")
                );
                list.add(q);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(rs, ps, cnn);
        }
    }

    public void DeleteQuestion(int id) throws Exception {
        //Open connection with sql
        Connection cnn = null;
        // move staement to sql server
        PreparedStatement ps = null;
        // get result from sql 
        ResultSet rs = null;
        DBContext db = DBContext.getInstance();
        try {
            String sql = "Delete from Question where id = ?";
            cnn = db.getConnection();
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(rs, ps, cnn);
        }
    }

    public int checkDuplicateQuestion(String question) throws Exception {
        //Open connection with sql
        Connection cnn = null;
        // move staement to sql server
        PreparedStatement ps = null;
        // get result from sql 
        ResultSet rs = null;
        DBContext db = DBContext.getInstance();
        try {
            String sql = "select COUNT(*) from Question where question = ?";
            cnn = db.getConnection();
            ps = cnn.prepareStatement(sql);
            ps.setString(1, question);
            rs = ps.executeQuery();
            // get number of account from sql 
            int result = 0;
            while (rs.next()) {
                result = rs.getInt(1);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(rs, ps, cnn);
        }
    }

    public List<Question> pagingManageQuiz(int paging, int index) throws Exception {
        //Open connection with sql
        Connection cnn = null;
        // move staement to sql server
        PreparedStatement ps = null;
        // get result from sql 
        ResultSet rs = null;
        DBContext db = DBContext.getInstance();
        List<Question> list = new ArrayList<>();
        try {
            String sql = "select * from \n"
                    + "(select ROW_NUMBER() over(order by id) as r, * from Question as q) as result\n"
                    + "where r between ? * ? - 5 and ? * ?";
            cnn = db.getConnection();
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, index);
            ps.setInt(2, paging);
            ps.setInt(3, index);
            ps.setInt(4, paging);
            rs = ps.executeQuery();
            while (rs.next()) {
                Question q = new Question(
                        rs.getInt("id"),
                        rs.getString("question"),
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getString("option4"),
                        rs.getString("answers"),
                        rs.getInt("user_id"),
                        rs.getString("created_by"),
                        rs.getDate("created_date")
                );
                list.add(q);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(rs, ps, cnn);
        }
    }
}
