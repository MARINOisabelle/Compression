import java.io.*;

public class LireBit extends FilterInputStream {
	int octet[] = new int[8];
	int cur;

	public LireBit(InputStream in) {
		super(in);
		cur = 0;
	}

	public boolean lire() throws IOException {
		int mask = 0x80;
		int o = this.read();
		// System.out.println(o);
		if (o == -1)
			return false;
		for (int i = 0; i < 8; i++) {
			if ((o & mask) == 0) {
				this.octet[i] = 0;
				mask = mask >> 1;
			} else {
				this.octet[i] = 1;
				mask = mask >> 1;
			}
		}
		return true;
	}

	public int lireBit() throws IOException {
		if (cur < 8) {
			cur++;
			return octet[cur - 1];

		}else {
			if (lire() != false) {
				cur = 1;
				return octet[0];
			} else {
				return -1;
			}
		}

	}

}
