<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:param name="userSkillLevel" select="'Beginner'"/>
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Recipes - XSL View</title>
                <link rel="stylesheet" href="/recipe-recommender/css/style.css"/>
                <style>
                    .recipe-yellow {
                        background-color: #fff9c4;
                        border-left: 4px solid #fbc02d;
                    }
                    .recipe-green {
                        background-color: #c8e6c9;
                        border-left: 4px solid #4caf50;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Recipes List (XSL Transform)</h1>
                    <p>User Skill Level: <strong><xsl:value-of select="$userSkillLevel"/></strong></p>
                    <a href="/recipe-recommender/">Home</a>
                    <div class="recipes-grid">
                        <xsl:apply-templates select="recipeData/recipes/recipe"/>
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="recipe">
        <div class="recipe-card">
            <xsl:attribute name="class">
                <xsl:choose>
                    <xsl:when test="difficultyLevel = $userSkillLevel">recipe-card recipe-yellow</xsl:when>
                    <xsl:otherwise>recipe-card recipe-green</xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>
            <h3><xsl:value-of select="title"/></h3>
            <p><strong>Difficulty:</strong> <xsl:value-of select="difficultyLevel"/></p>
            <p><strong>Cuisine Types:</strong>
                <xsl:for-each select="cuisineTypes/cuisine">
                    <xsl:value-of select="."/>
                    <xsl:if test="position() != last()">, </xsl:if>
                </xsl:for-each>
            </p>
            <a href="/recipe-recommender/recipe-details?id={@id}">View Details</a>
        </div>
    </xsl:template>

</xsl:stylesheet>

