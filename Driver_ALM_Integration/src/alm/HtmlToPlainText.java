package alm;

import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class HtmlToPlainText {

	private FormattingVisitor formatter;
	private static HtmlToPlainText instance;

	private HtmlToPlainText() {
		formatter = new FormattingVisitor();
	}

	static HtmlToPlainText getInstance() {
		if (instance == null) {
			instance = new HtmlToPlainText();
		}
		return instance;
	}

	/**
	 * Converts gherkin scenario from html to plain text and returns plain text
	 * scenario
	 * 
	 * @param scenario
	 * @return string
	 */
	public static String convertScenarioFromHtmlToPlainText(String scenario) {

		Document doc = Jsoup.parse(scenario);
		String plainText = "";
		Elements elements = doc.select("body");
		for (Element element : elements) {
			plainText = HtmlToPlainText.getInstance().getPlainText(element);
		}
		return plainText;
	}

	/**
	 * Format an Element to plain-text
	 * 
	 * @param element
	 * @return string
	 */
	public String getPlainText(Element element) {

		formatter.reset();
		NodeTraversor.traverse(formatter, element);
		return formatter.toString();
	}

	/**
	 * The formatting rules, implemented in a breadth-first DOM traverse
	 */
	private class FormattingVisitor implements NodeVisitor {

		private static final int maxWidth = 500;
		private int width = 0;
		private StringBuilder accum = new StringBuilder();

		void reset() {
			accum.setLength(0);
		}

		/**
		 * Hit when the node is first seen
		 * 
		 * @param node
		 * @param depth
		 */
		public void head(Node node, int depth) {

			String name = node.nodeName();
			if (node instanceof TextNode)
				append(((TextNode) node).text());
			else if (StringUtil.in(name, "p", "h1", "h2", "h3", "h4", "h5", "tr"))
				append("\n");
		}

		/**
		 * Hit when all of the node's children (if any) have been visited
		 * 
		 * @param node
		 * @param depth
		 */
		public void tail(Node node, int depth) {

			String name = node.nodeName();
			if (StringUtil.in(name, "br", "dd", "dt", "p", "h1", "h2", "h3", "h4", "h5", "div"))
				append("\n");
			else if (name.equals("a"))
				append(String.format(" <%s>", node.absUrl("href")));
		}

		/**
		 * Appends text to the string builder with a simple word wrap method.If the
		 * the original html contains newline (\n), it gets preserved otherwise if
		 * the original html contains br or div tags, they get translated to newline(\n).
		 * 
		 * @param text
		 */
		private void append(String text) {

			if (text.startsWith("\n"))
				width = 0;
			if (text.equals(" ")
					&& (accum.length() == 0 || StringUtil.in(accum.substring(accum.length() - 1), " ", "\n")))
				return;
			if (text.length() + width > maxWidth) {
				String[] words = text.split("\\s+");
				for (int i = 0; i < words.length; i++) {
					String word = words[i];
					boolean last = i == words.length - 1;
					if (!last)
						word = word + " ";
					if (word.length() + width > maxWidth) {
						accum.append("\n").append(word);
						width = word.length();
					} else {
						accum.append(word);
						width += word.length();
					}
				}
			} else {
				accum.append(text);
				width += text.length();
			}
		}

		@Override
		public String toString() {

			return accum.toString();
		}
	}
}
