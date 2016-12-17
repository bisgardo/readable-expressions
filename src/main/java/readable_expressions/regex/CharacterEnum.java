package readable_expressions.regex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CharacterEnum extends CharacterClassComponent {
	private final List<CharacterClassComponent> components = new ArrayList<CharacterClassComponent>();
	
	@Override
	public Iterator<Character> iterateMatches() {
		return Choice.join(new Iterator<Iterator<Character>>() {
			private final Iterator<CharacterClassComponent> iterator = components.iterator();
			
			//@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
			
			//@Override
			public Iterator<Character> next() {
				CharacterClassComponent component = iterator.next();
				if (component == null) {
					throw new NullPointerException("component");
				}
				return component.iterateMatches();
			}
			
			//@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		});
	}
	
	@Override
	public CharacterClassComponent or(CharacterClassComponent component) {
		if (component == null) {
			throw new NullPointerException("component");
		}
		components.add(component);
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (CharacterClassComponent component : components) {
			stringBuilder.append(component.toString());
		}
		return stringBuilder.toString();
	}
	
	@Override
	protected int getCardinality() {
		return components.size();
	}
}
