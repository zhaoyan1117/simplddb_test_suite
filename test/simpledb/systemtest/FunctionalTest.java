package simpledb.systemtest;

import simpledb.systemtest.SystemTestUtil;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.*;
import java.util.*;

import simpledb.*;

public class FunctionalTest extends SimpleDbTestBase {
    private SeqScan scan;
    private HeapFile file;

    private SeqScan emptyScan;
    private HeapFile emptyFile;

    private TransactionId tid1;
    private TransactionId tid2;

    @Before public void loadTable() {
        // construct a 3-column table schema
        Type types[] = new Type[]{ Type.INT_TYPE, Type.INT_TYPE, Type.INT_TYPE };
        String names[] = new String[]{ "field0", "field1", "field2" };
        TupleDesc descriptor = new TupleDesc(types, names);
        
        // create the table, associate it with some_data_file.dat
        // and tell the catalog about the schema of this table.
        this.file = new HeapFile(new File("test/simpledb/systemtest/data/data.dat"), descriptor);
        this.emptyFile = new HeapFile(new File("test/simpledb/systemtest/data/emptyFile.dat"), descriptor);
        Database.getCatalog().addTable(this.file, "file");
        Database.getCatalog().addTable(this.emptyFile, "emptyFile");
        
        // construct the query: we use a simple SeqScan, which spoonfeeds
        // tuples via its iterator.
        this.tid1 = new TransactionId();
        this.tid2 = new TransactionId();
        this.scan = new SeqScan(tid1, this.file.getId());
        this.emptyScan = new SeqScan(tid2, this.emptyFile.getId());
    }

    /**
     * SELECT count(*) FROM data.dat
     */
    @Test public void new_CountTest() {
        try {
            int count = 0;
            this.scan.open();
            while(this.scan.hasNext()) {
                Tuple tup = this.scan.next();
                count++;
            }
            this.scan.close();
            assertEquals(3, count); // Result should be 3.
            Database.getBufferPool().transactionComplete(this.tid1);
        } catch (Exception e) {
            System.out.println ("Exception : " + e);
            System.out.println ((Thread.currentThread().getStackTrace()));
        }
    }

    /**
     * SELECT count(*) FROM emptyFile.dat
     */
    @Test public void new_CountZeroTest() {
        try {
            int count = 0;
            this.emptyScan.open();
            while(this.emptyScan.hasNext()) {
                Tuple tup = this.emptyScan.next();
                count++;
            }
            this.emptyScan.close();
            assertEquals(0, count); // Result should be 0.
            Database.getBufferPool().transactionComplete(this.tid2);
        } catch (Exception e) {
            System.out.println ("Exception : " + e);
            System.out.println ((Thread.currentThread().getStackTrace()));
        }
    }
}
