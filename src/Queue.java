@SuppressWarnings("unchecked")
public class Queue<T>{

	// should I use eclim? maybe...
	private int cap;
	private int last;
	private int reader;
	private Object elements[];

	public Queue(int cap){
		this.elements=new Object[cap];
		this.cap=cap;	
		this.reader=0;
		this.last=0;
	}

	public void printAll(){
		System.out.println("reader:"+reader+" last:"+last+" cap:"+cap);
		for(int i=0;i<last;i++)
			System.out.print("["+elements[i].toString()+"] ");
		System.out.println();
	}

	public boolean add(T e){
		if(last==cap) return false;
		elements[last]=e;
		last++;
		return true;
	}

	public T get(){
		if(reader==cap) return null;
		T res=(T)elements[reader];
		++reader;
		return res;
	}

	public void empty(){
		this.elements=new Object[cap]; 
		this.last=0;
		this.reader=0;
	}

	public void resize(int newCap){
		assert(newCap>0);
		this.cap=newCap;
		initNewElements();
	}
	/**
		copy old elements into the newly resized elements and set 
		arguments like size and cap accordingly
	*/
	private void initNewElements(){ // for instance to 3rd. element || [2]
		Object[] newEs=new Object[this.cap];
		
		// get upper traversal bound
		int upperBound=this.cap;
		if(this.elements.length<this.cap)
			upperBound=this.elements.length;
		
		// begin copy
		int lastInitialized=0;
		for(int i=0;i<upperBound;++i)
			if(elements[i] != null){
				newEs[i]=elements[i];
				lastInitialized++;
			}
		
		// warning : there might be leaks, so if you give a shit,
		// write a function that rearranges elements arrays
		this.last=lastInitialized;
	}

	public int getCap(){return this.cap;}
	public T[] getArray(){return (T[])this.elements;}
	public void resetReader(){reader=0;} // for a new iteration

	public static void test(){
		Queue<String> q=new Queue<>(4);
		
		System.out.println((q.add("1st")?"[x]":"[]")+"addition");
		System.out.println((q.add("2nd")?"[x]":"[]")+"addition");
		System.out.println((q.add("3rd")?"[x]":"[]")+"addition");
		System.out.println((q.add("4th")?"[x]":"[]")+"addition");
		
		System.out.println((q.add("X")==false?"[x]":"[]")+"addition");
		System.out.println((q.add("X")==false?"[x]":"[]")+"addition");
		System.out.println((q.add("X")==false?"[x]":"[]")+"addition");
		System.out.println((q.add("X")==false?"[x]":"[]")+"addition");
		
		for(int i=0;i<q.getCap();i++) q.get();
		for(int i=0;i<2;i++) System.out.println((q.get()==null?"[x]":"[ ]")+"null");

		q.resize(q.getCap()-2);
		q.resetReader();
		for(int i=0;i<2;i++) System.out.println((q.get()!=null?"[x]":"[ ]")+"resize");
		for(int i=0;i<2;i++) System.out.println((q.get()==null?"[x]":"[ ]")+"resize-null");
	}
}
