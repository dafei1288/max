package com.dafei1288.max.data;

import com.dafei1288.max.collect.TupleList;
import com.dafei1288.max.collect.Tuples;
import com.dafei1288.max.collect.tuple.Tuple;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class SqlLoader implements DataLoader {
    public static Stream<Tuple> loadData(Connection conn, String sql,final Object...objects) throws SQLException {

//        try(){
            PreparedStatement ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            int cnt = 0;
            for (Object param : objects) {
                ps.setObject(++cnt, param);
            }
//            try () {
                ResultSet rs = ps.executeQuery();
                //rs.beforeFirst();
                return StreamSupport
                        .stream(Spliterators.spliteratorUnknownSize(
                                new Iterator(){

                                    @Override
                                    public boolean hasNext() {
                                        try{
                                            return rs.next();
                                        }catch(Exception e){
                                            e.printStackTrace();
                                            return false;
                                        }

                                    }

                                    @Override
                                    public Object next() {
                                        try{
                                            int cc = rs.getMetaData().getColumnCount();

                                            List<Object> row = new ArrayList<>();
//                                            IntStream.range(1,cc).map(it-> {
//
//                                                return rs.getObject(it);
//
//                                            }).collect(Collectors.toList());
                                            for(int i=1;i<=cc;i++){
                                                row.add(rs.getObject(i));
                                            }
                                            return Tuples.tuple(row.toArray());
                                        }catch(Throwable e){
                                            e.printStackTrace();
                                            return null;
                                        }

                                    }
                                }, 0), false);
            }

    public static TupleList<Tuple> loadDataAsTupleList(Connection conn, String sql, final Object...objects) throws SQLException {
        TupleList<Tuple> tl = new TupleList();
        try(PreparedStatement ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
            int cnt = 0;
            for (Object param : objects) {
                ps.setObject(++cnt, param);
            }
            try (ResultSet rs = ps.executeQuery();) {
                int cc = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    List<Object> row = new ArrayList<>();
                    for (int i = 1; i <= cc; i++) {
                        row.add(rs.getObject(i));
                    }
                    tl.add(Tuples.tuple(row.toArray()));
                }
            }
        }

        return tl;
    }
}
