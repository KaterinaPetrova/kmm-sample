//
//  BabiesViewController.swift
//  CheerUP
//
//  Created by eppetrova on 20.04.2020.
//  Copyright © 2020 eppetrova. All rights reserved.
//

import UIKit
import SharedCode
import SDWebImage

class GifsViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    @IBOutlet var collectionView: UICollectionView!
    @IBOutlet var loadingIndicator: UIActivityIndicatorView!
    
    var gifService: GifService!
    var selectedGifTypes: [GifType] = []
    
    private var gifs = [Gif]()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = selectedGifTypes.map{  $0.title }.joined(separator: " + ")
        loadingIndicator.startAnimating()
        gifService.getGifs(types: selectedGifTypes, completionHandler: { (result, error) in
            if let gifs = result {
                self.gifs = gifs
                self.collectionView.reloadData()
                self.loadingIndicator.stopAnimating()
            } else if let error = error {
                print(error)
            }
        })
    }
    
    // MARK: UICollectionViewDataSourse, UICollectionViewDelegate
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
       return gifs.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
       let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "BabyCell", for: indexPath) as! GifCollectionViewCell
        cell.imageView.sd_setImage(with: URL(string: gifs[indexPath.row].images.previewImage.url))
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let gif = gifs[indexPath.row]
        let ratio = collectionView.bounds.width / CGFloat(gif.images.previewImage.width)
        let height = CGFloat(gif.images.previewImage.height) * ratio
        return CGSize(width: collectionView.bounds.width, height: height)
    }
}
