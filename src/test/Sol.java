package test;

public class Sol {
	
	String word = ((Text)(mouseEvent.getSource())).getText();
	OccurenceData occurenceData = extractedMap.get(word);
	String sRegExSearch = "\\W(?i:" + word + ")\\W";
	final String HTMLBoundingBlueBoxCSSstyle = "<style>.border{border-style:solid</string>"
	StringBuilder stringBuilder = new StringBuilder(HTMLBoundingBlueBoxCSSStyle);
	for (Paragraph paragraph : occurenceData.collectionParagraphs) {
		String hilightedString = paragraph.toString().replaceAll(sRegExSearch, "<");
		stringBuilder.append(hilightedString).append("<br><br>");
		
	}
	
	webView.getEngine().loadContent(stringBuilder.toString());
}
