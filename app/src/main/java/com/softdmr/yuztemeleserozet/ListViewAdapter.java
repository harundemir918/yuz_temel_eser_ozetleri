package com.softdmr.yuztemeleserozet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<Book> bookList;
    private ArrayList<Book> arrayList;

    // ListViewAdapter'in yapıcı metodu oluşturuldu
    public ListViewAdapter(Context context, List<Book> bookList) {
        mContext = context;
        this.bookList = bookList;
        layoutInflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(bookList);
    }

    // ViewHolder nesnesi oluşturuldu ve içine TextView-ImageView tanımlamaları yapıldı
    public class ViewHolder {
        TextView bookNameText, bookAuthorText;
        ImageView bookCoverImage;
    }

    // Listedeki kitapların sayısı döndürüldü
    @Override
    public int getCount() {
        return bookList.size();
    }

    // Verilen pozisyondaki kitap döndürüldü
    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    // Pozisyon değeri döndürüldü
    @Override
    public long getItemId(int position) {
        return position;
    }

    /* Liste için gereken View oluşturuldu,
    listenin elemanlarının yerleştirileceği "row" elemanı tanımlandı ve atamalar yapıldı
    */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.row, null);

            viewHolder.bookNameText = convertView.findViewById(R.id.titleText);
            viewHolder.bookAuthorText = convertView.findViewById(R.id.authorText);
            viewHolder.bookCoverImage = convertView.findViewById(R.id.bookImage);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bookNameText.setText(bookList.get(position).getName());
        viewHolder.bookAuthorText.setText(bookList.get(position).getAuthor());
        if (bookList.get(position).getCover() != null && bookList.get(position).getCover().length() > 0) {
            Picasso.get().load(bookList.get(position).getCover()).into(viewHolder.bookCoverImage);
        }

        // Listedeki herhangi bir kitap seçildiğinde yapılacaklar sıralandı
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = bookList.get(position).getName();
                String bookSummary = bookList.get(position).getSummary();
                Intent summaryIntent = new Intent(mContext, SummaryActivity.class);
                summaryIntent.putExtra("bookName", bookName);
                summaryIntent.putExtra("bookSummary", bookSummary);
                summaryIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(summaryIntent);
            }
        });

        return convertView;
    }


    // Kitap arama metodu için filtre oluşturuldu
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        bookList.clear();
        if (charText.length() == 0) {
            bookList.addAll(arrayList);
        }
        else {
            for (Book book : arrayList) {
                if (book.getName().toLowerCase(Locale.getDefault()).contains(charText) ||
                        book.getAuthor().toLowerCase(Locale.getDefault()).contains(charText)) {
                    bookList.add(book);
                }
            }
        }
        notifyDataSetChanged();
    }
}
