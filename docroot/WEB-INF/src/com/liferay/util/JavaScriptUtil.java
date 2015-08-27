package com.liferay.util;

public class JavaScriptUtil 
{
    public static String markupToStringLiteral(String text) 
    {		
    	return markupToStringLiteral(text,false);
    }
    
    public static String markupToStringLiteral(String text,boolean addQuotation) 
    {	   	
    	String encoded = toStringLiteral(text);
    	  	    	
    	encoded = markupToStringLiteralProcessScriptTag(encoded);    	
  	
        if (addQuotation)
            return "\"" + encoded + "\"";
        else
            return encoded.toString();    	
    }  
    
    protected static String markupToStringLiteralProcessScriptTag(String encoded)
    {
    	String encodedLowerCase = encoded.toLowerCase();  // Así admitimos </SCRIPT>, </script> etc
		int len = "</script>".length();  
		int addedChars = 0;
    	int startInLowerCase = 0;
    	int posInLowerCase;
    	do
    	{
	    	posInLowerCase = encodedLowerCase.indexOf("</script>",startInLowerCase);
	    	if (posInLowerCase != -1)
	    	{
	    		int posInOriginal = posInLowerCase + addedChars;
	    		String scriptEndTagName = encoded.substring(posInOriginal + 2, posInOriginal + len - 1); // El original
	    		encoded = encoded.substring(0, posInOriginal) + "<\\/" + scriptEndTagName + ">" + encoded.substring(posInOriginal + len);
	    		addedChars++;
	    		startInLowerCase = posInLowerCase + len;
	    	}
    	}
    	while(posInLowerCase != -1);  
    	return encoded;
    }
    
    
    protected static String toStringLiteral(String text) 
    {
        StringBuilder encoded = new StringBuilder(text);
        for (int i = 0; i < encoded.length(); i++) 
        {
            char c = encoded.charAt(i);
            switch (c) 
            {
            case '\r':
                encoded.deleteCharAt(i); // Caso Windows (CR), debería seguir un LF (\n)
                i--;
                break;
            case '\n':
                encoded.deleteCharAt(i);
                encoded.insert(i, "\\n");
                i++;
                break;
            case '"':
                encoded.deleteCharAt(i);
                encoded.insert(i, "\\\"");
                i++;
                break;
            case '\'':
                encoded.deleteCharAt(i);
                encoded.insert(i, "\\'");
                i++;
                break;
            case '\\':
                encoded.deleteCharAt(i);
                encoded.insert(i, "\\\\");
                i++;
                break;
            case '\t':
                encoded.deleteCharAt(i);
                encoded.insert(i, "\\t");
                i++;
                break;
            case '\f':
                encoded.deleteCharAt(i); // FORM FEED
                encoded.insert(i, "\\f");
                i++;
                break;
            case '\b':
                encoded.deleteCharAt(i); // BACK SPACE
                encoded.insert(i, "\\b");
                i++;
                break;
            }            
        }
        return encoded.toString();
    }

}
