package com.recipe.servlet;

import com.recipe.model.User;
import com.recipe.util.XMLUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
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
        InputStream xslStream = null;
        
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

            // Load XSL file - try multiple locations
            xslStream = getServletContext().getResourceAsStream("/WEB-INF/xsl/recipes.xsl");
            if (xslStream == null) {
                xslStream = getClass().getClassLoader().getResourceAsStream("xsl/recipes.xsl");
            }
            if (xslStream == null) {
                String xslPath = getServletContext().getRealPath("/WEB-INF/xsl/recipes.xsl");
                if (xslPath != null && new File(xslPath).exists()) {
                    xslStream = new java.io.FileInputStream(xslPath);
                }
            }

            if (xslStream == null) {
                throw new IOException("Could not find XSL file: /WEB-INF/xsl/recipes.xsl");
            }

            // Get the Document from XMLUtil (already loaded)
            org.w3c.dom.Document document = xmlUtil.getDocument();
            if (document == null) {
                throw new IOException("XML document is not loaded. XMLUtil initialization may have failed.");
            }

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslStream));

            transformer.setParameter("userSkillLevel", userSkillLevel);

            response.setContentType("text/html;charset=UTF-8");
            
            // Transform using the Document from XMLUtil
            transformer.transform(
                new DOMSource(document),
                new StreamResult(response.getWriter())
            );

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error transforming XML: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (xslStream != null) {
                try {
                    xslStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

