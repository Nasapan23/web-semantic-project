package com.recipe.servlet;

import com.recipe.model.User;
import com.recipe.util.XMLUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class XSLTransformServlet extends HttpServlet {
    private XMLUtil xmlUtil;

    @Override
    public void init() throws ServletException {
        xmlUtil = new XMLUtil();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String userId = request.getParameter("userId");
            User user = null;
            if (userId != null && !userId.trim().isEmpty()) {
                user = xmlUtil.getUserById(userId);
            } else {
                user = xmlUtil.getFirstUser();
            }

            String userSkillLevel = "Beginner";
            if (user != null) {
                userSkillLevel = user.getCookingSkillLevel();
            }

            String xmlPath = getServletContext().getRealPath("/WEB-INF/classes/data/recipes.xml");
            String xslPath = getServletContext().getRealPath("/WEB-INF/xsl/recipes.xsl");

            InputStream xmlStream = null;
            InputStream xslStream = null;

            if (xmlPath != null && new File(xmlPath).exists()) {
                xmlStream = new java.io.FileInputStream(xmlPath);
            } else {
                xmlStream = getClass().getClassLoader().getResourceAsStream("data/recipes.xml");
            }

            if (xslPath != null && new File(xslPath).exists()) {
                xslStream = new java.io.FileInputStream(xslPath);
            } else {
                xslStream = getServletContext().getResourceAsStream("/WEB-INF/xsl/recipes.xsl");
                if (xslStream == null) {
                    xslStream = getClass().getClassLoader().getResourceAsStream("xsl/recipes.xsl");
                }
            }

            if (xmlStream == null || xslStream == null) {
                throw new IOException("Could not find XML or XSL files");
            }

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslStream));

            transformer.setParameter("userSkillLevel", userSkillLevel);

            response.setContentType("text/html;charset=UTF-8");
            transformer.transform(
                new StreamSource(xmlStream),
                new StreamResult(response.getWriter())
            );

            if (xmlStream != null) xmlStream.close();
            if (xslStream != null) xslStream.close();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error transforming XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

