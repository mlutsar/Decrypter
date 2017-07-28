import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Decrypter {

	public static final String encrypted = "NPLSRGSYZMCRZEHQCTZSBMDVRPFODBCLWSQYSNNVEMKDDDSXQZTVTLDZAKGXCNAOXXCHASNAWPXDDXMFKSERBPJETKOXXXELXZFKEMOCPRTVRHTILGKOOOCLKCRWLREDXDSXCJLDPFWZWOCGMCRZEHQCTZSRRLLPZVEDXNZGLFBVOBXRTCZNXOYYFGNLMSZGDZTMSBOGXDSBCRHKWBXATBEBMFEKCMRPHXWRKDLEXIDTHXXTNPTLZNDLGKOOOCLKCRSDBDDVYXIEETDTHXLEKMBVTMIERZTVKWEIZGOLLCFFODMRPTNGXBDTBJAKDTMWTCDBMLEMZFZFMOCTXOBCCXCEKSNMOOMYCTXOHWTSOOIYWRXZFSLEDTFOMNDTYYYXXZPKDLEXXCEAKEMRPTNGXBDTBJAKDTAFTXENWNHWANDPKDSXXHASNAMWTCDBMLEMCRZEHCJLDPFCLKODXMFKOLGNHASNAKCXXZMAFTXENWNHWANDLMSZGEDXCCNVPLGSBMSTBPGOHTXONXTGDFBDTOODHWPLEMKYFMSYXCDNMSTCNHWANDTGQEAOBNKYMEXYYFKSPKDCTXDYYCFMLGLPIOCYYCFOOXHAHXPGDTTVWRPLLDPKDSTXMRMWTCDBMLEMZFZFMOCLRZPOGXBEASDBCYHDQHBQKOPMRPFOEAYOLDZBXANDLGNZNDANDEAOOTDLYBZFDSXPZNBTXBEKKYLPZKWLKOGXBJKODMBTVDPWRPGMPYSYWSYZAFTXENWLEQZKSEAWDKOWBODHXHTVVBXRTPTGOWBXPUOEPOPGEDBXRXHEKKAHGPKGSBVPUOTGQWBWTMOOBXDHWPBWAHBETXEPKJL";

	public static final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();

	public static void main(String[] args) {
		Map<String, ArrayList<Integer>> map = seeWhereTriplesHappen();
		for (String key : map.keySet()) {
			String locations = map.get(key).stream().map(i -> i.toString())
					.collect(Collectors.joining(", "));
			System.out.println(key + ": " + locations);
		}
		List<String> subSeqs = makeSubSeqs();
		computeFrequencies(subSeqs);

		ArrayList<String> decSeqs = new ArrayList<String>();
		// shiftid sisestan ma käsitsi
		decSeqs.add(rotate(subSeqs.get(0), 10));
		decSeqs.add(rotate(subSeqs.get(1), 11));
		decSeqs.add(rotate(subSeqs.get(2), -7));

		String decrypted = recombineSeqs(decSeqs);
		System.out.println(decrypted);

	}

	private static String rotate(String crypted, Integer shift) {
		String dec = "";
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < crypted.length(); i++) {
			char currentchar = crypted.charAt(i);
			int pos = alphabet.indexOf(Character.toString(currentchar));
			dec = dec + alphabet.charAt((pos - shift + 26) % 26);
		}

		return dec;
	}

	private static Map<String, ArrayList<Integer>> seeWhereTriplesHappen() {
		Map<String, ArrayList<Integer>> map = new HashMap<>();

		for (int i = 0; i < encrypted.length() - 3; i++) {
			String tripleInQuestion = encrypted.substring(i, i + 3);
			if (map.containsKey(tripleInQuestion)) {
				map.get(tripleInQuestion).add(i);
			} else {
				ArrayList<Integer> list = new ArrayList<>();
				list.add(i);
				map.put(tripleInQuestion, list);
			}
		}
		return map;
	}

	private static List<String> makeSubSeqs() {
		String seq1 = "";
		String seq2 = "";
		String seq3 = "";

		for (int i = 0; i < encrypted.length(); i++) {
			String cha = Character.toString(encrypted.charAt(i));

			if (i % 3 == 0) {
				seq1 = seq1 + cha;
			} else if (i % 3 == 1) {
				seq2 = seq2 + cha;
			} else {
				seq3 = seq3 + cha;
			}
		}
		List<String> threeSubs = new ArrayList<>();
		threeSubs.add(0, seq1);
		threeSubs.add(1, seq2);
		threeSubs.add(2, seq3);

		return threeSubs;
	}

	private static String recombineSeqs(List<String> seqs) {
		String decrypted = "";

		for (int i = 0; i < seqs.get(0).length(); i++) {
			for (int j = 0; j < seqs.size(); j++) {
				decrypted = decrypted
						+ Character.toString(seqs.get(j).charAt(i));
			}
		}

		return decrypted;
	}

	private static void computeFrequencies(List<String> subSeqs) {
		for (String seq : subSeqs) {
			System.out.println("New sequence");
			int total = seq.length();
			System.out.println("total " + total);
			for (char c : alphabet) {
				int count = StringUtils.countMatches(seq, c);
				float ratio = (float) count / total;
				System.out.println(c + ": " + ratio + ", ");
			}
			System.out.println();
		}
	}

}
