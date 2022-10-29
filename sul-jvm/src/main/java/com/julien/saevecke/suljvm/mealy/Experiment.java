package com.julien.saevecke.suljvm.mealy;

import net.automatalib.automata.transducers.impl.compact.CompactMealy;
import net.automatalib.serialization.dot.DOTParsers;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Configuration
public class Experiment {
    private final Map<String, String> models;

    public Experiment() {
        this.models = Map.of(
                "linux-tcp-server","digraph G {\n" +
                        "label=\"\"\n" +
                        "s0 [color=\"red\"]\n" +
                        "s1\n" +
                        "s2\n" +
                        "s3\n" +
                        "s4\n" +
                        "s5\n" +
                        "s6\n" +
                        "s7\n" +
                        "s8\n" +
                        "s9\n" +
                        "s10\n" +
                        "s11\n" +
                        "s12\n" +
                        "s13\n" +
                        "s14\n" +
                        "s15\n" +
                        "s16\n" +
                        "s17\n" +
                        "s18\n" +
                        "s19\n" +
                        "s20\n" +
                        "s21\n" +
                        "s22\n" +
                        "s23\n" +
                        "s24\n" +
                        "s25\n" +
                        "s26\n" +
                        "s27\n" +
                        "s28\n" +
                        "s29\n" +
                        "s30\n" +
                        "s31\n" +
                        "s32\n" +
                        "s33\n" +
                        "s34\n" +
                        "s35\n" +
                        "s36\n" +
                        "s37\n" +
                        "s38\n" +
                        "s39\n" +
                        "s40\n" +
                        "s41\n" +
                        "s42\n" +
                        "s43\n" +
                        "s44\n" +
                        "s45\n" +
                        "s46\n" +
                        "s47\n" +
                        "s48\n" +
                        "s49\n" +
                        "s50\n" +
                        "s51\n" +
                        "s52\n" +
                        "s53\n" +
                        "s54\n" +
                        "s55\n" +
                        "s56\n" +
                        "s0 [label=\"s0\"];\n" +
                        "s0 -> s0[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s0 -> s0[label=\"ACK+PSH(V,V,1)/RST(ZERO,ZERO,0)\"]\n" +
                        "s0 -> s0[label=\"SYN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s0 -> s0[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s0 -> s0[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s0 -> s0[label=\"FIN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s0 -> s1[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s0 -> s0[label=\"SYN(V,V,0)/ACK+RST(ZERO,NEXT,0)\"]\n" +
                        "s0 -> s0[label=\"RCV/TIMEOUT\"]\n" +
                        "s0 -> s0[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s0 -> s2[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s0 -> s0[label=\"ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s1 [label=\"s1\"];\n" +
                        "s1 -> s1[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s1 -> s1[label=\"ACK+PSH(V,V,1)/RST(ZERO,ZERO,0)\"]\n" +
                        "s1 -> s1[label=\"SYN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s1 -> s1[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s1 -> s4[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s1 -> s1[label=\"FIN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s1 -> s1[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s1 -> s3[label=\"SYN(V,V,0)/ACK+SYN(FRESH,NEXT,0)\"]\n" +
                        "s1 -> s1[label=\"RCV/TIMEOUT\"]\n" +
                        "s1 -> s1[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s1 -> s2[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s1 -> s1[label=\"ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s2 [label=\"s2\"];\n" +
                        "s2 -> s2[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s2 -> s2[label=\"ACK+PSH(V,V,1)/RST(ZERO,ZERO,0)\"]\n" +
                        "s2 -> s2[label=\"SYN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s2 -> s2[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s2 -> s2[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s2 -> s2[label=\"FIN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s2 -> s2[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s2 -> s2[label=\"SYN(V,V,0)/ACK+RST(ZERO,NEXT,0)\"]\n" +
                        "s2 -> s2[label=\"RCV/TIMEOUT\"]\n" +
                        "s2 -> s2[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s2 -> s2[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s2 -> s2[label=\"ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s3 [label=\"s3\"];\n" +
                        "s3 -> s3[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s3 -> s5[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s3 -> s1[label=\"SYN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s3 -> s1[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s3 -> s9[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s3 -> s7[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s3 -> s3[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s3 -> s1[label=\"SYN(V,V,0)/ACK+RST(ZERO,NEXT,0)\"]\n" +
                        "s3 -> s3[label=\"RCV/TIMEOUT\"]\n" +
                        "s3 -> s1[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s3 -> s8[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s3 -> s6[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s4 [label=\"s4\"];\n" +
                        "s4 -> s1[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s4 -> s4[label=\"ACK+PSH(V,V,1)/RST(ZERO,ZERO,0)\"]\n" +
                        "s4 -> s4[label=\"SYN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s4 -> s4[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s4 -> s4[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s4 -> s4[label=\"FIN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s4 -> s4[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s4 -> s9[label=\"SYN(V,V,0)/ACK+SYN(FRESH,NEXT,0)\"]\n" +
                        "s4 -> s4[label=\"RCV/TIMEOUT\"]\n" +
                        "s4 -> s4[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s4 -> s2[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s4 -> s4[label=\"ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s5 [label=\"s5\"];\n" +
                        "s5 -> s5[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s5 -> s5[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s5 -> s5[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s5 -> s12[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s5 -> s11[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s5 -> s10[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s5 -> s5[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s5 -> s5[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s5 -> s5[label=\"RCV/TIMEOUT\"]\n" +
                        "s5 -> s12[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s5 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s5 -> s5[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s6 [label=\"s6\"];\n" +
                        "s6 -> s6[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s6 -> s5[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s6 -> s6[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s6 -> s12[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s6 -> s13[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s6 -> s7[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s6 -> s6[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s6 -> s6[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s6 -> s6[label=\"RCV/TIMEOUT\"]\n" +
                        "s6 -> s12[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s6 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s6 -> s6[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s7 [label=\"s7\"];\n" +
                        "s7 -> s7[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s7 -> s7[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s7 -> s7[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s7 -> s12[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s7 -> s14[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s7 -> s7[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s7 -> s7[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s7 -> s7[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s7 -> s7[label=\"RCV/TIMEOUT\"]\n" +
                        "s7 -> s12[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s7 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s7 -> s7[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s8 [label=\"s8\"];\n" +
                        "s8 -> s8[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s8 -> s2[label=\"ACK+PSH(V,V,1)/RST(NEXT,ZERO,0)\"]\n" +
                        "s8 -> s2[label=\"SYN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s8 -> s2[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s8 -> s8[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s8 -> s2[label=\"FIN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s8 -> s8[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s8 -> s2[label=\"SYN(V,V,0)/ACK+RST(ZERO,NEXT,0)\"]\n" +
                        "s8 -> s8[label=\"RCV/TIMEOUT\"]\n" +
                        "s8 -> s2[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s8 -> s8[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s8 -> s2[label=\"ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s9 [label=\"s9\"];\n" +
                        "s9 -> s3[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s9 -> s11[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s9 -> s4[label=\"SYN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s9 -> s4[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s9 -> s9[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s9 -> s14[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s9 -> s9[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s9 -> s4[label=\"SYN(V,V,0)/ACK+RST(ZERO,NEXT,0)\"]\n" +
                        "s9 -> s9[label=\"RCV/TIMEOUT\"]\n" +
                        "s9 -> s4[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s9 -> s8[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s9 -> s13[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s10 [label=\"s10\"];\n" +
                        "s10 -> s10[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s10 -> s10[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s10 -> s10[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s10 -> s12[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s10 -> s15[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s10 -> s10[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s10 -> s10[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s10 -> s10[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s10 -> s10[label=\"RCV/TIMEOUT\"]\n" +
                        "s10 -> s12[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s10 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s10 -> s10[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s11 [label=\"s11\"];\n" +
                        "s11 -> s1[label=\"CLOSECONNECTION/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s11 -> s11[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s11 -> s11[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s11 -> s17[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s11 -> s11[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s11 -> s15[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s11 -> s11[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s11 -> s11[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s11 -> s13[label=\"RCV/TIMEOUT\"]\n" +
                        "s11 -> s17[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s11 -> s16[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s11 -> s11[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s12 [label=\"s12\"];\n" +
                        "s12 -> s12[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s12 -> s12[label=\"ACK+PSH(V,V,1)/RST(ZERO,ZERO,0)\"]\n" +
                        "s12 -> s12[label=\"SYN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s12 -> s12[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s12 -> s17[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s12 -> s12[label=\"FIN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s12 -> s12[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s12 -> s18[label=\"SYN(V,V,0)/ACK+SYN(FRESH,NEXT,0)\"]\n" +
                        "s12 -> s12[label=\"RCV/TIMEOUT\"]\n" +
                        "s12 -> s12[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s12 -> s2[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s12 -> s12[label=\"ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s13 [label=\"s13\"];\n" +
                        "s13 -> s19[label=\"CLOSECONNECTION/ACK+FIN(NEXT,CURRENT,0)\"]\n" +
                        "s13 -> s11[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s13 -> s13[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s13 -> s17[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s13 -> s13[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s13 -> s14[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s13 -> s13[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s13 -> s13[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s13 -> s20[label=\"RCV/TIMEOUT\"]\n" +
                        "s13 -> s17[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s13 -> s21[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s13 -> s13[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s14 [label=\"s14\"];\n" +
                        "s14 -> s23[label=\"CLOSECONNECTION/ACK+FIN(NEXT,CURRENT,0)\"]\n" +
                        "s14 -> s14[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s14 -> s14[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s14 -> s17[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s14 -> s14[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s14 -> s14[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s14 -> s14[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s14 -> s14[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s14 -> s14[label=\"RCV/TIMEOUT\"]\n" +
                        "s14 -> s17[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s14 -> s22[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s14 -> s14[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s15 [label=\"s15\"];\n" +
                        "s15 -> s1[label=\"CLOSECONNECTION/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s15 -> s15[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s15 -> s15[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s15 -> s17[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s15 -> s15[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s15 -> s15[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s15 -> s15[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s15 -> s15[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s15 -> s14[label=\"RCV/TIMEOUT\"]\n" +
                        "s15 -> s17[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s15 -> s24[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s15 -> s15[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s16 [label=\"s16\"];\n" +
                        "s16 -> s2[label=\"CLOSECONNECTION/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s16 -> s16[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s16 -> s16[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s16 -> s2[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s16 -> s16[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s16 -> s24[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s16 -> s16[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s16 -> s16[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s16 -> s21[label=\"RCV/TIMEOUT\"]\n" +
                        "s16 -> s2[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s16 -> s16[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s16 -> s16[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s17 [label=\"s17\"];\n" +
                        "s17 -> s1[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s17 -> s17[label=\"ACK+PSH(V,V,1)/RST(ZERO,ZERO,0)\"]\n" +
                        "s17 -> s17[label=\"SYN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s17 -> s17[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s17 -> s17[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s17 -> s17[label=\"FIN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s17 -> s17[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s17 -> s25[label=\"SYN(V,V,0)/ACK+SYN(FRESH,NEXT,0)\"]\n" +
                        "s17 -> s17[label=\"RCV/TIMEOUT\"]\n" +
                        "s17 -> s17[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s17 -> s2[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s17 -> s17[label=\"ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s18 [label=\"s18\"];\n" +
                        "s18 -> s18[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s18 -> s28[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s18 -> s12[label=\"SYN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s18 -> s12[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s18 -> s25[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s18 -> s26[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s18 -> s18[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s18 -> s12[label=\"SYN(V,V,0)/ACK+RST(ZERO,NEXT,0)\"]\n" +
                        "s18 -> s18[label=\"RCV/TIMEOUT\"]\n" +
                        "s18 -> s12[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s18 -> s8[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s18 -> s27[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s19 [label=\"s19\"];\n" +
                        "s19 -> s19[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s19 -> s1[label=\"ACK+PSH(V,V,1)/RST(NEXT,ZERO,0)\"]\n" +
                        "s19 -> s19[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s19 -> s1[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s19 -> s31[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s19 -> s32[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s19 -> s19[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s19 -> s19[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s19 -> s19[label=\"RCV/TIMEOUT\"]\n" +
                        "s19 -> s1[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s19 -> s29[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s19 -> s30[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s20 [label=\"s20\"];\n" +
                        "s20 -> s19[label=\"CLOSECONNECTION/ACK+FIN(NEXT,CURRENT,0)\"]\n" +
                        "s20 -> s13[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s20 -> s20[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s20 -> s17[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s20 -> s20[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s20 -> s14[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s20 -> s20[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s20 -> s20[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s20 -> s20[label=\"RCV/TIMEOUT\"]\n" +
                        "s20 -> s17[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s20 -> s21[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s20 -> s20[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s21 [label=\"s21\"];\n" +
                        "s21 -> s29[label=\"CLOSECONNECTION/ACK+FIN(NEXT,CURRENT,0)\"]\n" +
                        "s21 -> s16[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s21 -> s21[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s21 -> s2[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s21 -> s21[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s21 -> s22[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s21 -> s21[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s21 -> s21[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s21 -> s33[label=\"RCV/TIMEOUT\"]\n" +
                        "s21 -> s2[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s21 -> s21[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s21 -> s21[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s22 [label=\"s22\"];\n" +
                        "s22 -> s34[label=\"CLOSECONNECTION/ACK+FIN(NEXT,CURRENT,0)\"]\n" +
                        "s22 -> s22[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s22 -> s22[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s22 -> s2[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s22 -> s22[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s22 -> s22[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s22 -> s22[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s22 -> s22[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s22 -> s22[label=\"RCV/TIMEOUT\"]\n" +
                        "s22 -> s2[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s22 -> s22[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s22 -> s22[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s23 [label=\"s23\"];\n" +
                        "s23 -> s23[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s23 -> s35[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s23 -> s23[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s23 -> s1[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s23 -> s36[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s23 -> s35[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s23 -> s23[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s23 -> s23[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s23 -> s23[label=\"RCV/TIMEOUT\"]\n" +
                        "s23 -> s1[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s23 -> s34[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s23 -> s35[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s24 [label=\"s24\"];\n" +
                        "s24 -> s2[label=\"CLOSECONNECTION/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s24 -> s24[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s24 -> s24[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s24 -> s2[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s24 -> s24[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s24 -> s24[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s24 -> s24[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s24 -> s24[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s24 -> s22[label=\"RCV/TIMEOUT\"]\n" +
                        "s24 -> s2[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s24 -> s24[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s24 -> s24[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s25 [label=\"s25\"];\n" +
                        "s25 -> s3[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s25 -> s37[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s25 -> s17[label=\"SYN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s25 -> s17[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s25 -> s25[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s25 -> s38[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s25 -> s25[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s25 -> s17[label=\"SYN(V,V,0)/ACK+RST(ZERO,NEXT,0)\"]\n" +
                        "s25 -> s25[label=\"RCV/TIMEOUT\"]\n" +
                        "s25 -> s17[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s25 -> s8[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s25 -> s39[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s26 [label=\"s26\"];\n" +
                        "s26 -> s26[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s26 -> s26[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s26 -> s26[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s26 -> s40[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s26 -> s38[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s26 -> s26[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s26 -> s26[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s26 -> s26[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s26 -> s26[label=\"RCV/TIMEOUT\"]\n" +
                        "s26 -> s40[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s26 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s26 -> s26[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s27 [label=\"s27\"];\n" +
                        "s27 -> s27[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s27 -> s28[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s27 -> s27[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s27 -> s40[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s27 -> s39[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s27 -> s26[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s27 -> s27[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s27 -> s27[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s27 -> s27[label=\"RCV/TIMEOUT\"]\n" +
                        "s27 -> s40[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s27 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s27 -> s27[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s28 [label=\"s28\"];\n" +
                        "s28 -> s28[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s28 -> s28[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s28 -> s28[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s28 -> s40[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s28 -> s37[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s28 -> s41[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s28 -> s28[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s28 -> s28[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s28 -> s28[label=\"RCV/TIMEOUT\"]\n" +
                        "s28 -> s40[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s28 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s28 -> s28[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s29 [label=\"s29\"];\n" +
                        "s29 -> s29[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s29 -> s2[label=\"ACK+PSH(V,V,1)/RST(NEXT,ZERO,0)\"]\n" +
                        "s29 -> s29[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s29 -> s2[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s29 -> s29[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s29 -> s43[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s29 -> s29[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s29 -> s29[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s29 -> s29[label=\"RCV/TIMEOUT\"]\n" +
                        "s29 -> s2[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s29 -> s29[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s29 -> s42[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s30 [label=\"s30\"];\n" +
                        "s30 -> s30[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s30 -> s1[label=\"ACK+PSH(V,V,1)/RST(NEXT,ZERO,0)\"]\n" +
                        "s30 -> s1[label=\"SYN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s30 -> s1[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s30 -> s44[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s30 -> s32[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s30 -> s30[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s30 -> s1[label=\"SYN(V,V,0)/ACK+RST(ZERO,NEXT,0)\"]\n" +
                        "s30 -> s30[label=\"RCV/TIMEOUT\"]\n" +
                        "s30 -> s1[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s30 -> s42[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s30 -> s30[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s31 [label=\"s31\"];\n" +
                        "s31 -> s19[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s31 -> s4[label=\"ACK+PSH(V,V,1)/RST(NEXT,ZERO,0)\"]\n" +
                        "s31 -> s31[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s31 -> s4[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s31 -> s31[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s31 -> s45[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s31 -> s31[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s31 -> s31[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s31 -> s31[label=\"RCV/TIMEOUT\"]\n" +
                        "s31 -> s4[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s31 -> s29[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s31 -> s44[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s32 [label=\"s32\"];\n" +
                        "s32 -> s32[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s32 -> s32[label=\"ACK+PSH(V,V,1)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s32 -> s32[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s32 -> s1[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s32 -> s45[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s32 -> s32[label=\"FIN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s32 -> s32[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s32 -> s32[label=\"SYN(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s32 -> s32[label=\"RCV/TIMEOUT\"]\n" +
                        "s32 -> s1[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s32 -> s43[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s32 -> s32[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s33 [label=\"s33\"];\n" +
                        "s33 -> s29[label=\"CLOSECONNECTION/ACK+FIN(NEXT,CURRENT,0)\"]\n" +
                        "s33 -> s21[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s33 -> s33[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s33 -> s2[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s33 -> s33[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s33 -> s22[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s33 -> s33[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s33 -> s33[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s33 -> s33[label=\"RCV/TIMEOUT\"]\n" +
                        "s33 -> s2[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s33 -> s21[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s33 -> s33[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s34 [label=\"s34\"];\n" +
                        "s34 -> s34[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s34 -> s8[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s34 -> s34[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s34 -> s2[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s34 -> s34[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s34 -> s8[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s34 -> s34[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s34 -> s34[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s34 -> s34[label=\"RCV/TIMEOUT\"]\n" +
                        "s34 -> s2[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s34 -> s34[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s34 -> s8[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s35 [label=\"s35\"];\n" +
                        "s35 -> s35[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s35 -> s1[label=\"ACK+PSH(V,V,1)/RST(NEXT,ZERO,0)\"]\n" +
                        "s35 -> s1[label=\"SYN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s35 -> s1[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s35 -> s46[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s35 -> s1[label=\"FIN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s35 -> s35[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s35 -> s3[label=\"SYN(V,V,0)/ACK+SYN(FRESH,NEXT,0)\"]\n" +
                        "s35 -> s35[label=\"RCV/TIMEOUT\"]\n" +
                        "s35 -> s1[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s35 -> s8[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s35 -> s1[label=\"ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s36 [label=\"s36\"];\n" +
                        "s36 -> s23[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s36 -> s46[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s36 -> s36[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s36 -> s4[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s36 -> s36[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s36 -> s46[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s36 -> s36[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s36 -> s36[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s36 -> s36[label=\"RCV/TIMEOUT\"]\n" +
                        "s36 -> s4[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s36 -> s34[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s36 -> s46[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s37 [label=\"s37\"];\n" +
                        "s37 -> s5[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s37 -> s37[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s37 -> s37[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s37 -> s48[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s37 -> s37[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s37 -> s47[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s37 -> s37[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s37 -> s37[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s37 -> s37[label=\"RCV/TIMEOUT\"]\n" +
                        "s37 -> s48[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s37 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s37 -> s37[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s38 [label=\"s38\"];\n" +
                        "s38 -> s7[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s38 -> s38[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s38 -> s38[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s38 -> s48[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s38 -> s38[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s38 -> s38[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s38 -> s38[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s38 -> s38[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s38 -> s38[label=\"RCV/TIMEOUT\"]\n" +
                        "s38 -> s48[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s38 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s38 -> s38[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s39 [label=\"s39\"];\n" +
                        "s39 -> s6[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s39 -> s37[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s39 -> s39[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s39 -> s48[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s39 -> s39[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s39 -> s38[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s39 -> s39[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s39 -> s39[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s39 -> s39[label=\"RCV/TIMEOUT\"]\n" +
                        "s39 -> s48[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s39 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s39 -> s39[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s40 [label=\"s40\"];\n" +
                        "s40 -> s40[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s40 -> s40[label=\"ACK+PSH(V,V,1)/RST(ZERO,ZERO,0)\"]\n" +
                        "s40 -> s40[label=\"SYN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s40 -> s40[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s40 -> s48[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s40 -> s40[label=\"FIN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s40 -> s40[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s40 -> s49[label=\"SYN(V,V,0)/ACK+SYN(FRESH,NEXT,0)\"]\n" +
                        "s40 -> s40[label=\"RCV/TIMEOUT\"]\n" +
                        "s40 -> s40[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s40 -> s2[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s40 -> s40[label=\"ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s41 [label=\"s41\"];\n" +
                        "s41 -> s41[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s41 -> s41[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s41 -> s41[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s41 -> s40[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s41 -> s47[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s41 -> s41[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s41 -> s41[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s41 -> s41[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s41 -> s41[label=\"RCV/TIMEOUT\"]\n" +
                        "s41 -> s40[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s41 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s41 -> s41[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s42 [label=\"s42\"];\n" +
                        "s42 -> s42[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s42 -> s2[label=\"ACK+PSH(V,V,1)/RST(NEXT,ZERO,0)\"]\n" +
                        "s42 -> s2[label=\"SYN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s42 -> s2[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s42 -> s42[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s42 -> s43[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s42 -> s42[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s42 -> s2[label=\"SYN(V,V,0)/ACK+RST(ZERO,NEXT,0)\"]\n" +
                        "s42 -> s42[label=\"RCV/TIMEOUT\"]\n" +
                        "s42 -> s2[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s42 -> s42[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s42 -> s42[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s43 [label=\"s43\"];\n" +
                        "s43 -> s43[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s43 -> s43[label=\"ACK+PSH(V,V,1)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s43 -> s43[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s43 -> s2[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s43 -> s43[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s43 -> s43[label=\"FIN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s43 -> s43[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s43 -> s43[label=\"SYN(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s43 -> s43[label=\"RCV/TIMEOUT\"]\n" +
                        "s43 -> s2[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s43 -> s43[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s43 -> s43[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s44 [label=\"s44\"];\n" +
                        "s44 -> s30[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s44 -> s4[label=\"ACK+PSH(V,V,1)/RST(NEXT,ZERO,0)\"]\n" +
                        "s44 -> s4[label=\"SYN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s44 -> s4[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s44 -> s44[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s44 -> s45[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s44 -> s44[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s44 -> s4[label=\"SYN(V,V,0)/ACK+RST(ZERO,NEXT,0)\"]\n" +
                        "s44 -> s44[label=\"RCV/TIMEOUT\"]\n" +
                        "s44 -> s4[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s44 -> s42[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s44 -> s44[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s45 [label=\"s45\"];\n" +
                        "s45 -> s32[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s45 -> s45[label=\"ACK+PSH(V,V,1)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s45 -> s45[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s45 -> s4[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s45 -> s45[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s45 -> s45[label=\"FIN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s45 -> s45[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s45 -> s45[label=\"SYN(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s45 -> s45[label=\"RCV/TIMEOUT\"]\n" +
                        "s45 -> s4[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s45 -> s43[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s45 -> s45[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s46 [label=\"s46\"];\n" +
                        "s46 -> s35[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s46 -> s4[label=\"ACK+PSH(V,V,1)/RST(NEXT,ZERO,0)\"]\n" +
                        "s46 -> s4[label=\"SYN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s46 -> s4[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s46 -> s46[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s46 -> s4[label=\"FIN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s46 -> s46[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s46 -> s9[label=\"SYN(V,V,0)/ACK+SYN(FRESH,NEXT,0)\"]\n" +
                        "s46 -> s46[label=\"RCV/TIMEOUT\"]\n" +
                        "s46 -> s4[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s46 -> s8[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s46 -> s4[label=\"ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s47 [label=\"s47\"];\n" +
                        "s47 -> s10[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s47 -> s47[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s47 -> s47[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s47 -> s48[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s47 -> s47[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s47 -> s47[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s47 -> s47[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s47 -> s47[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s47 -> s47[label=\"RCV/TIMEOUT\"]\n" +
                        "s47 -> s48[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s47 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s47 -> s47[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s48 [label=\"s48\"];\n" +
                        "s48 -> s12[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s48 -> s48[label=\"ACK+PSH(V,V,1)/RST(ZERO,ZERO,0)\"]\n" +
                        "s48 -> s48[label=\"SYN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s48 -> s48[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s48 -> s48[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s48 -> s48[label=\"FIN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s48 -> s48[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s48 -> s50[label=\"SYN(V,V,0)/ACK+SYN(FRESH,NEXT,0)\"]\n" +
                        "s48 -> s48[label=\"RCV/TIMEOUT\"]\n" +
                        "s48 -> s48[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s48 -> s2[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s48 -> s48[label=\"ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s49 [label=\"s49\"];\n" +
                        "s49 -> s49[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s49 -> s49[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s49 -> s40[label=\"SYN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s49 -> s40[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s49 -> s50[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s49 -> s49[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s49 -> s49[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s49 -> s40[label=\"SYN(V,V,0)/ACK+RST(ZERO,NEXT,0)\"]\n" +
                        "s49 -> s49[label=\"RCV/TIMEOUT\"]\n" +
                        "s49 -> s40[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s49 -> s8[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s49 -> s49[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s50 [label=\"s50\"];\n" +
                        "s50 -> s18[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s50 -> s52[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s50 -> s48[label=\"SYN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s50 -> s48[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s50 -> s50[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s50 -> s53[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s50 -> s50[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s50 -> s48[label=\"SYN(V,V,0)/ACK+RST(ZERO,NEXT,0)\"]\n" +
                        "s50 -> s50[label=\"RCV/TIMEOUT\"]\n" +
                        "s50 -> s48[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s50 -> s8[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s50 -> s51[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s51 [label=\"s51\"];\n" +
                        "s51 -> s27[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s51 -> s52[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s51 -> s51[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s51 -> s54[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s51 -> s51[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s51 -> s53[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s51 -> s51[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s51 -> s51[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s51 -> s51[label=\"RCV/TIMEOUT\"]\n" +
                        "s51 -> s54[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s51 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s51 -> s51[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s52 [label=\"s52\"];\n" +
                        "s52 -> s28[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s52 -> s52[label=\"ACK+PSH(V,V,1)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s52 -> s52[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s52 -> s54[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s52 -> s52[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s52 -> s55[label=\"FIN+ACK(V,V,0)/ACK(NEXT,NEXT,0)\"]\n" +
                        "s52 -> s52[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s52 -> s52[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s52 -> s52[label=\"RCV/TIMEOUT\"]\n" +
                        "s52 -> s54[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s52 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s52 -> s52[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s53 [label=\"s53\"];\n" +
                        "s53 -> s26[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s53 -> s53[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s53 -> s53[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s53 -> s54[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s53 -> s53[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s53 -> s53[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s53 -> s53[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s53 -> s53[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s53 -> s53[label=\"RCV/TIMEOUT\"]\n" +
                        "s53 -> s54[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s53 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s53 -> s53[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s54 [label=\"s54\"];\n" +
                        "s54 -> s40[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s54 -> s54[label=\"ACK+PSH(V,V,1)/RST(ZERO,ZERO,0)\"]\n" +
                        "s54 -> s54[label=\"SYN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s54 -> s54[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s54 -> s54[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s54 -> s54[label=\"FIN+ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s54 -> s54[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s54 -> s56[label=\"SYN(V,V,0)/ACK+SYN(FRESH,NEXT,0)\"]\n" +
                        "s54 -> s54[label=\"RCV/TIMEOUT\"]\n" +
                        "s54 -> s54[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s54 -> s2[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s54 -> s54[label=\"ACK(V,V,0)/RST(ZERO,ZERO,0)\"]\n" +
                        "s55 [label=\"s55\"];\n" +
                        "s55 -> s41[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s55 -> s55[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s55 -> s55[label=\"SYN+ACK(V,V,0)/ACK(NEXT,CURRENT,0)\"]\n" +
                        "s55 -> s54[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s55 -> s55[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s55 -> s55[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s55 -> s55[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s55 -> s55[label=\"SYN(V,V,0)/TIMEOUT\"]\n" +
                        "s55 -> s55[label=\"RCV/TIMEOUT\"]\n" +
                        "s55 -> s54[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s55 -> s2[label=\"CLOSE/ACK+RST(NEXT,CURRENT,0)\"]\n" +
                        "s55 -> s55[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s56 [label=\"s56\"];\n" +
                        "s56 -> s49[label=\"CLOSECONNECTION/TIMEOUT\"]\n" +
                        "s56 -> s56[label=\"ACK+PSH(V,V,1)/TIMEOUT\"]\n" +
                        "s56 -> s54[label=\"SYN+ACK(V,V,0)/RST(NEXT,ZERO,0)\"]\n" +
                        "s56 -> s54[label=\"RST(V,V,0)/TIMEOUT\"]\n" +
                        "s56 -> s56[label=\"ACCEPT/TIMEOUT\"]\n" +
                        "s56 -> s56[label=\"FIN+ACK(V,V,0)/TIMEOUT\"]\n" +
                        "s56 -> s56[label=\"LISTEN/TIMEOUT\"]\n" +
                        "s56 -> s54[label=\"SYN(V,V,0)/ACK+RST(ZERO,NEXT,0)\"]\n" +
                        "s56 -> s56[label=\"RCV/TIMEOUT\"]\n" +
                        "s56 -> s54[label=\"ACK+RST(V,V,0)/TIMEOUT\"]\n" +
                        "s56 -> s8[label=\"CLOSE/TIMEOUT\"]\n" +
                        "s56 -> s56[label=\"ACK(V,V,0)/TIMEOUT\"]\n" +
                        "\n" +
                        "__start0 [label=\"\" shape=\"none\" width=\"0\" height=\"0\"];\n" +
                        "__start0 -> s0;\n" +
                        "}",
                "coffee", "digraph g {\n" +
                        "\n" +
                        "\ts0 [shape=\"circle\" label=\"0\"];\n" +
                        "\ts1 [shape=\"circle\" label=\"1\"];\n" +
                        "\ts2 [shape=\"circle\" label=\"2\"];\n" +
                        "\ts3 [shape=\"circle\" label=\"3\"];\n" +
                        "\ts4 [shape=\"circle\" label=\"4\"];\n" +
                        "\ts5 [shape=\"circle\" label=\"5\"];\n" +
                        "\ts0 -> s1 [label=\"POD / ok\"];\n" +
                        "\ts0 -> s0 [label=\"CLEAN / ok\"];\n" +
                        "\ts0 -> s2 [label=\"WATER / ok\"];\n" +
                        "\ts0 -> s3 [label=\"BUTTON / error\"];\n" +
                        "\ts1 -> s1 [label=\"POD / ok\"];\n" +
                        "\ts1 -> s0 [label=\"CLEAN / ok\"];\n" +
                        "\ts1 -> s4 [label=\"WATER / ok\"];\n" +
                        "\ts1 -> s3 [label=\"BUTTON / error\"];\n" +
                        "\ts2 -> s4 [label=\"POD / ok\"];\n" +
                        "\ts2 -> s0 [label=\"CLEAN / ok\"];\n" +
                        "\ts2 -> s2 [label=\"WATER / ok\"];\n" +
                        "\ts2 -> s3 [label=\"BUTTON / error\"];\n" +
                        "\ts3 -> s3 [label=\"POD / error\"];\n" +
                        "\ts3 -> s3 [label=\"CLEAN / error\"];\n" +
                        "\ts3 -> s3 [label=\"WATER / error\"];\n" +
                        "\ts3 -> s3 [label=\"BUTTON / error\"];\n" +
                        "\ts4 -> s4 [label=\"POD / ok\"];\n" +
                        "\ts4 -> s0 [label=\"CLEAN / ok\"];\n" +
                        "\ts4 -> s4 [label=\"WATER / ok\"];\n" +
                        "\ts4 -> s5 [label=\"BUTTON / coffee!\"];\n" +
                        "\ts5 -> s3 [label=\"POD / error\"];\n" +
                        "\ts5 -> s0 [label=\"CLEAN / ok\"];\n" +
                        "\ts5 -> s3 [label=\"WATER / error\"];\n" +
                        "\ts5 -> s3 [label=\"BUTTON / error\"];\n" +
                        "\n" +
                        "__start0 [label=\"\" shape=\"none\" width=\"0\" height=\"0\"];\n" +
                        "__start0 -> s0;\n" +
                        "\n" +
                        "}\n" +
                        "\n"
                );
    }

    public CompactMealy<String, String> create(String type) throws IOException {
        var graphModel = DOTParsers.mealy().readModel(new ByteArrayInputStream(models.get(type).getBytes(StandardCharsets.UTF_8)));
        return graphModel.model;
    }
}
