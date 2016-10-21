package jacobrawlinson.assignmentapplication;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wolf Gevaudan on 07/12/2015.
 */
public class xmlDetailedPullParser {

    public String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();
        String dbno = "";
        String foodName = "";
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("food")) {
                String[] details = readName(parser);
                dbno = details[0];
                foodName = details[1];
                entries.add(readItem(parser, foodName, dbno));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    public static class xmlItem {
        public final String foodName;
        public final String databaseNumber;
        public final String calories;
        public final String protein;
        public final String carbohydrate;
        public final String sugars;
        public final String fat;
        public final String saturates;
        public final String fibre;
        public final String salt;

        private xmlItem(String foodName, String databaseNumber, String calories, String protein, String carbohydrate, String sugars, String fat, String saturates, String fibre, String salt) {
            this.foodName = foodName;
            this.databaseNumber = databaseNumber;
            this.calories = calories;
            this.protein = protein;
            this.carbohydrate = carbohydrate;
            this.sugars = sugars;
            this.fat = fat;
            this.saturates = saturates;
            this.fibre = fibre;
            this.salt = salt;
        }
    }

    private xmlItem readItem(XmlPullParser parser, String foodname, String dbno) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "food");
        String foodName = foodname;
        String databaseNumber = dbno;
        String calories = null;
        String protein = null;
        String carbohydrate = null;
        String sugars = null;
        String fat = null;
        String saturates = null;
        String fibre = null;
        String salt = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("nutrients")) {
                String[] nuts = readNutrients(parser);
                calories = nuts[0];
                protein = nuts[1];
                carbohydrate = nuts[2];
                sugars = nuts[3];
                fat = nuts[4];
                saturates = nuts[5];
                fibre = nuts[6];
                salt = nuts[7];
            } else {
                    skip(parser);
            }
        }
        return new xmlItem(foodName, databaseNumber, calories, protein, carbohydrate, sugars, fat, saturates, fibre, salt);
    }

    private String[] readName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "food");
        String dbno = parser.getAttributeValue(0);
        String name = parser.getAttributeValue(1);
        String[] details = new String[]{dbno, name};
        return details;
    }

    private String[] readNutrients(XmlPullParser parser)throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, ns, "nutrients");
        String calories = null;
        String protein = null;
        String carbohydrate = null;
        String sugars = null;
        String fat = null;
        String saturates = null;
        String fibre = null;
        String salt = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("nutrient")) {
                String tagId = parser.getAttributeValue(0);
                if (tagId.equals("208")) {
                    calories = readNutrient(parser);
                } else if (tagId.equals("203")) {
                    protein = readNutrient(parser);
                } else if (tagId.equals("205")) {
                    carbohydrate = readNutrient(parser);
                } else if (tagId.equals("269")) {
                    sugars = readNutrient(parser);
                } else if (tagId.equals("204")) {
                    fat = readNutrient(parser);
                } else if (tagId.equals("606")) {
                    saturates = readNutrient(parser);
                } else if (tagId.equals("291")) {
                    fibre = readNutrient(parser);
                } else if (tagId.equals("307")) {
                    salt = readNutrient(parser);
                }
                skip(parser);
            }
            else {
                skip(parser);
            }
        }
        String[] nuts = new String[]{calories, protein, carbohydrate, sugars, fat, saturates, fibre, salt};

        return nuts;
    }

    private String readNutrient(XmlPullParser parser) throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, ns, "nutrient");
        String nutrient = parser.getAttributeValue(3);
        return nutrient;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
