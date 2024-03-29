package main;

import java.util.*;
import java.util.stream.Collectors;

import static main.PokerTypeEnum.*;

public class PokerType {

    public static int judgeCardsType(List<Card> cardList) {
        Map<Integer, Integer> map = checkCards(cardList);

        if (map.size() == 4) {
            return PAIR;
        } else if (map.size() == 3 && maxMapValue(map) == 2) {
            return TWO_PAIR;
        } else if (map.size() == 3 && maxMapValue(map) == 3) {
            return THREE_OF_KIND;
        } else if (map.size() == 2 && maxMapValue(map) == 4) {
            return FOUR_OF_KIND;
        } else if (map.size() == 2) {
            return FUll_HOUSE;
        } else if (isStraight(cardList) && !isFlush(cardList)){
            return STRAIGHT;
        }else if (isFlush(cardList) && !isStraight(cardList)) {
            return FLUSH;
        }else if (isStraight(cardList) && isFlush(cardList)){
            return STRAIGHT_FLUSH;
        }

        return HIGHT_CARD;
    }

    private static boolean isFlush(List<Card> cardList) {
        for(int i=1;i<cardList.size();i++){
            if(cardList.get(i).getSuit() != cardList.get(i-1).getSuit()){
                return false;
            }
        }
        return true;
    }

    public static Map<Integer, Integer> checkCards(List<Card> cardList) {
        int count = 1;
        List<Card> cards = cardList.stream().sorted(Comparator.comparing(Card::getNumber)).collect(Collectors.toList());
        Map<Integer, Integer> cardMap = new HashMap<>();
        cardMap.put(cards.get(0).getNumber(), count);
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getNumber() == cards.get(i - 1).getNumber()) {
                count++;
                cardMap.put(cards.get(i).getNumber(), count);
            } else {
                count = 1;
                cardMap.put(cards.get(i).getNumber(), count);
            }
        }
        return cardMap;
    }

    private static int maxMapValue(Map<Integer, Integer> map) {
        List<Integer> values = new ArrayList<>(map.values());
        Collections.sort(values);
        return values.get(values.size() - 1);
    }

    private static boolean isStraight(List<Card> cardList) {
        for(int i=1;i<cardList.size();i++){
            if(cardList.get(i).getNumber() != cardList.get(i-1).getNumber() + 1){
                return false;
            }
        }
        return true;
    }
}
