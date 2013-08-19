package com.example.emenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

public class DishActivity extends FragmentActivity implements
		DishTypeFragment.OnDishTypeSelectedListener,
		DishListFragment.OnDishListSelectedListener {

	DishTypeFragment mDishTypeFragment;
	DishListFragment mDishListFragment;
	DishFragment mDishFragment;
	List<List<HashMap<String, String>>> totalList;
	List<HashMap<String, String>> dishDataCollection;
	List<HashMap<String, String>> cusineDataCollection;

	String cusine[] = { "Veg Starters", "Non Veg starters", "Veg Curry" };

	static final String KEY_TAG = "dish"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_RATE = "rate";
	static final String KEY_RATING = "rating";
	static final String KEY_SPICY = "spicy";
	static final String KEY_DESCRIPTION = "description";
	static final String KEY_ICON = "icon";
	static final String KEY_SHORT = "shortdescription";
	static final String KEY_XMLTAG = "tag";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish);
		mDishTypeFragment = (DishTypeFragment) getSupportFragmentManager()
				.findFragmentById(R.id.dishtype_fragment);
		mDishFragment = (DishFragment) getSupportFragmentManager()
				.findFragmentById(R.id.dish_fragment);
		mDishListFragment = (DishListFragment) getSupportFragmentManager()
				.findFragmentById(R.id.dishList_fragment);
		parseDishList();
		mDishTypeFragment.setCusineCollection(cusineDataCollection);
		onDishTypeSelected(0);
		onDishListSelected(0, totalList.get(0).get(0));
	}

	public void parseDishList() {
		try {

			dishDataCollection = new ArrayList<HashMap<String, String>>();
			cusineDataCollection = new ArrayList<HashMap<String, String>>();
			totalList = new ArrayList<List<HashMap<String, String>>>();

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

			// for Cusine list
			Document cusinedoc = docBuilder.parse(getAssets()
					.open("cusine.xml"));
			cusinedoc.getDocumentElement().normalize();
			// for dish list
			Document dishdoc = docBuilder.parse(getAssets().open("dish.xml"));
			// normalize text representation
			dishdoc.getDocumentElement().normalize();

			NodeList cusineList = cusinedoc.getElementsByTagName("cusine");

			// NodeList dishList = dishdoc.getElementsByTagName("vegstarters");

			cusineDataCollection = extractCusineData(cusineList);

			System.out.println("cusinelist size" + cusineDataCollection.size());

			for (int i = 0; i < cusineDataCollection.size(); i++) {
				System.out.println("cusine item: "
						+ cusineDataCollection.get(i).get(KEY_NAME));
				NodeList dishList = dishdoc
						.getElementsByTagName(cusineDataCollection.get(i).get(
								KEY_XMLTAG));
				totalList.add(extractDishData(dishList));
			}

			// this.dishDataCollection = extract(dishList);
			System.out.println("Length of total dsi data: " + totalList.size());
			
		} catch (IOException ex) {
			Log.e("Error", ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private List<HashMap<String, String>> extractCusineData(NodeList cusineList) {
		HashMap<String, String> map = null;
		List<HashMap<String, String>> cusineDataCollection = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < cusineList.getLength(); i++) {
			map = new HashMap<String, String>();

			Node firstDishNode = cusineList.item(i);

			if (firstDishNode.getNodeType() == Node.ELEMENT_NODE) {

				Element firstDishElement = (Element) firstDishNode;
				// 1.-------
				NodeList idList = firstDishElement.getElementsByTagName(KEY_ID);
				Element idElement = (Element) idList.item(0);
				NodeList textIdList = idElement.getChildNodes();

				map.put(KEY_ID, ((Node) textIdList.item(0)).getNodeValue()
						.trim());

				// 2.-------
				NodeList name = firstDishElement.getElementsByTagName(KEY_NAME);
				Element nameElement = (Element) name.item(0);
				NodeList nameList = nameElement.getChildNodes();

				map.put(KEY_NAME, ((Node) nameList.item(0)).getNodeValue()
						.trim());
				// 3.
				// 2.-------
				NodeList tag = firstDishElement
						.getElementsByTagName(KEY_XMLTAG);
				Element tagElement = (Element) tag.item(0);
				NodeList tagList = tagElement.getChildNodes();

				map.put(KEY_XMLTAG, ((Node) tagList.item(0)).getNodeValue()
						.trim());

				cusineDataCollection.add(map);
			}
		}

		return cusineDataCollection;
	}

	public List<HashMap<String, String>> extractDishData(NodeList dishList) {
		HashMap<String, String> map = null;
		List<HashMap<String, String>> dishDataCollection = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < dishList.getLength(); i++) {
			map = new HashMap<String, String>();

			Node firstDishNode = dishList.item(i);

			if (firstDishNode.getNodeType() == Node.ELEMENT_NODE) {

				Element firstDishElement = (Element) firstDishNode;
				// 1.-------
				NodeList idList = firstDishElement.getElementsByTagName(KEY_ID);
				Element idElement = (Element) idList.item(0);
				NodeList textIdList = idElement.getChildNodes();

				map.put(KEY_ID, ((Node) textIdList.item(0)).getNodeValue()
						.trim());

				// 2.-------
				NodeList name = firstDishElement.getElementsByTagName(KEY_NAME);
				Element nameElement = (Element) name.item(0);
				NodeList nameList = nameElement.getChildNodes();

				map.put(KEY_NAME, ((Node) nameList.item(0)).getNodeValue()
						.trim());

				// 3.-------
				NodeList rate = firstDishElement.getElementsByTagName(KEY_RATE);
				Element rateElement = (Element) rate.item(0);
				NodeList rateList = rateElement.getChildNodes();
				// --city
				map.put(KEY_RATE, ((Node) rateList.item(0)).getNodeValue()
						.trim());

				// 4.-------
				NodeList rating = firstDishElement
						.getElementsByTagName(KEY_RATING);
				Element ratingElement = (Element) rating.item(0);
				NodeList ratingList = ratingElement.getChildNodes();
				// --city
				map.put(KEY_RATING, ((Node) ratingList.item(0)).getNodeValue()
						.trim());

				// 5.-------
				NodeList spicy = firstDishElement
						.getElementsByTagName(KEY_SPICY);
				Element spicyElement = (Element) spicy.item(0);
				NodeList spicyList = spicyElement.getChildNodes();
				// --city
				map.put(KEY_SPICY, ((Node) spicyList.item(0)).getNodeValue()
						.trim());

				// 6.-------
				NodeList description = firstDishElement
						.getElementsByTagName(KEY_DESCRIPTION);
				Element descriptionElement = (Element) description.item(0);
				NodeList descriptionList = descriptionElement.getChildNodes();

				map.put(KEY_DESCRIPTION, ((Node) descriptionList.item(0))
						.getNodeValue().trim());

				// 7.-------
				NodeList shortDescription = firstDishElement
						.getElementsByTagName(KEY_SHORT);
				Element shortDescriptionElement = (Element) shortDescription
						.item(0);
				NodeList shortDescriptionList = shortDescriptionElement
						.getChildNodes();
				// --city
				map.put(KEY_SHORT, ((Node) shortDescriptionList.item(0))
						.getNodeValue().trim());
				// 8

				NodeList icon = firstDishElement.getElementsByTagName(KEY_ICON);
				Element firstIconElement = (Element) icon.item(0);
				NodeList iconList = firstIconElement.getChildNodes();
				// --city
				map.put(KEY_ICON, ((Node) iconList.item(0)).getNodeValue()
						.trim());
				// Add to the Arraylist
				dishDataCollection.add(map);
			}
		}

		return dishDataCollection;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dish, menu);
		return true;
	}

	@Override
	public void onDishListSelected(int position,
			HashMap<String, String> dishDetails) {
		// TODO Auto-generated method stub

		if (mDishFragment != null) {
			mDishFragment.updateDishView(position, dishDetails);
		}
	}

	@Override
	public void onDishTypeSelected(int position) {
		// TODO Auto-generated method stub

		if (mDishListFragment != null) {
			mDishListFragment.showItem(totalList.get(position));
		}
	}

}
