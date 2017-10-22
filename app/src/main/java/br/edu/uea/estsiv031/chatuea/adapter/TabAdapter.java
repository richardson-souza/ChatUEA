package br.edu.uea.estsiv031.chatuea.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.edu.uea.estsiv031.chatuea.fragment.ContatosFragment;
import br.edu.uea.estsiv031.chatuea.fragment.ConversaFragment;

/**
 * Created by rsouza on 17/10/17.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private String[] tituloAbas = {"CONVERSAS", "CONTATOS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new ConversaFragment();
                break;
            case 1:
                fragment = new ContatosFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }
}
