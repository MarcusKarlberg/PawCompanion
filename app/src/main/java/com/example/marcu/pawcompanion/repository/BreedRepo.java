package com.example.marcu.pawcompanion.repository;


import com.example.marcu.pawcompanion.data.Breed;

import java.util.*;

/**
 * Created by marcu on 3/18/2018.
 */

public final class BreedRepo {

    private List<Breed> breeds = new LinkedList<>();
    private List<Breed> filteredBreeds = new LinkedList<>();

    public BreedRepo() {
        breeds.add(new Breed(1,"Australian Sheperd",5 ,3 ));
        breeds.add(new Breed(2,"Affenpinscher",3,1));
        breeds.add(new Breed(3,"Afghan Hound",4,4));
        breeds.add(new Breed(4,"Airedale Terrier",5,3));
        breeds.add(new Breed(5,"Akita",4,4));
        breeds.add(new Breed(6,"Alaskan Malamute",5,4));
        breeds.add(new Breed(7,"American English Coonhound",5,3));
        breeds.add(new Breed(8,"American Eskimo Dog",4,2));
        breeds.add(new Breed(9,"American Foxhound",5,3));
        breeds.add(new Breed(10,"American Hairless Terrier",4,1));
        breeds.add(new Breed(12,"American Staffordshire Terrier",3,3));
        breeds.add(new Breed(13,"American Water Spaniel",5,3));
        breeds.add(new Breed(14,"Anatolian Shepherd Dog",3,5));
        breeds.add(new Breed(15,"Appenzeller Sennenhund",5,3));
        breeds.add(new Breed(16,"Australian Cattle Dog",5,3));
        breeds.add(new Breed(17,"Australian Kelpie",5,3));
        breeds.add(new Breed(19,"Australian Terrier",4,1));
        breeds.add(new Breed(20,"Azawakh",4,3));
        breeds.add(new Breed(21,"Barbet",3,3));
        breeds.add(new Breed(22,"Basenji",5,2));
        breeds.add(new Breed(24,"Basset Hound",2,2));
        breeds.add(new Breed(26,"Beagle",5 ,2 ));
        breeds.add(new Breed(27,"Bearded Collie",4,3));
        breeds.add(new Breed(29,"Bedlington Terrier",4,3));
        breeds.add(new Breed(31,"Belgian Malinois",5,4));
        breeds.add(new Breed(32,"Belgian Sheepdog",5,4));
        breeds.add(new Breed(33,"Belgian Tervuren",4,4));
        breeds.add(new Breed(35,"Berger Picard",5,4));
        breeds.add(new Breed(36,"Bernese Mountain Dog",4 ,5 ));
        breeds.add(new Breed(37,"Bichon Frise",4,2));
        breeds.add(new Breed(39,"Black and Tan Coonhound",5,3));
        breeds.add(new Breed(40,"Black Russian Terrier",4,5));
        breeds.add(new Breed(41,"Bloodhound",4,4));
        breeds.add(new Breed(42,"Bluetick Coonhound",5,3));
        breeds.add(new Breed(43,"Boerboel",4,5));
        breeds.add(new Breed(44,"Bolognese",3,1));
        breeds.add(new Breed(45,"Border Collie",5,3));
        breeds.add(new Breed(46,"Borzoi",2,5));
        breeds.add(new Breed(47,"Boston Terrier",4,2));
        breeds.add(new Breed(48,"Bouvier des Flandres",4,4));
        breeds.add(new Breed(49,"Boxer",5,3));
        breeds.add(new Breed(50,"Boykin Spaniel",5,3));
        breeds.add(new Breed(51,"Bracco Italiano",4,4));
        breeds.add(new Breed(54,"Briard",4,4));
        breeds.add(new Breed(55,"Brittany",5,3));
        breeds.add(new Breed(57,"Brussels Griffon",4,1));
        breeds.add(new Breed(58,"Bull Terrier",5,2));
        breeds.add(new Breed(59, "Bulldog",3,2));
        breeds.add(new Breed(61,"Bullmastiff",4,5));
        breeds.add(new Breed(62,"Cairn Terriers",4,1));
        breeds.add(new Breed(63,"Canaan",3,3));
        breeds.add(new Breed(64,"Cane Corso",5,4));
        breeds.add(new Breed(65,"Cardigan Welsh Corgi",3,2));
        breeds.add(new Breed(66,"Catahoula Leopard",5,4));
        breeds.add(new Breed(67,"Caucasian Shepherd",3,5));
        breeds.add(new Breed(68,"Cavalier King Charles Spaniel",4,2));
        breeds.add(new Breed(69,"Cesky Terrier",3,2));
        breeds.add(new Breed(70,"Chesapeake Bay Retriever",5,3));
        breeds.add(new Breed(71,"Chihuahua",2,1));
        breeds.add(new Breed(72,"Chinese Crested",2,2));
        breeds.add(new Breed(73,"Chinese Shar Pei",2,3));
        breeds.add(new Breed(74,"Chinook",3,4));
        breeds.add(new Breed(75,"Chow Chow",2,4));
        breeds.add(new Breed(76,"Clumber Spaniel",2,3));
        breeds.add(new Breed(77,"Cocker Spaniel",3,2));
        breeds.add(new Breed(78,"Collie",3,3));
        breeds.add(new Breed(79,"Coton de Tulear",3,1));
        breeds.add(new Breed(80,"Curly Coated Retriever",4,3));
        breeds.add(new Breed(81,"Dachshund",3,1));
        breeds.add(new Breed(82,"Dalmatian",5,3));
        breeds.add(new Breed(83,"Dandie Dinmont Terrier",2,1));
        breeds.add(new Breed(84,"Doberman Pinscher",4,4));
        breeds.add(new Breed(85,"Dogo Argentino",4,4));
        breeds.add(new Breed(86,"Dogue de Bordeaux",5,4));
        breeds.add(new Breed(87,"Dutch Shepherd",4,3));
        breeds.add(new Breed(88,"English Cocker Spaniel",4,2));
        breeds.add(new Breed(89,"English Foxhound",5,3));
        breeds.add(new Breed(90,"English Setter",5,3));
        breeds.add(new Breed(91,"English Springer Spaniel",5,3));
        breeds.add(new Breed(92,"English Toy Spaniel",2,2));
        breeds.add(new Breed(93,"Entlebucher Mountain Dog",5,3));
        breeds.add(new Breed(94,"Field Spaniel",5,2));
        breeds.add(new Breed(95,"Finnish Lapphund",4,3));
        breeds.add(new Breed(96,"Finnish Spitz",5,3));
        breeds.add(new Breed(97,"Flat Coated Retriever",5,3));
        breeds.add(new Breed(98,"French Bulldog",2 ,2 ));
        breeds.add(new Breed(99,"German Pinscher",5,3));
        breeds.add(new Breed(100, "German Shepherd", 5, 4));
        breeds.add(new Breed(101,"German Shorthaired Pointer",5,3));
        breeds.add(new Breed(102,"German Wirehaired Pointer",5,4));
        breeds.add(new Breed(103,"Giant Schnauzer",5,4));
        breeds.add(new Breed(104,"Glen of Imaal Terrier",4,1));
        breeds.add(new Breed(105,"Golden Retriever",5,3));
        breeds.add(new Breed(106,"Gordon Setter",5,3));
        breeds.add(new Breed(107,"Great Dane",5,5));
        breeds.add(new Breed(108,"Great Pyrenees",5,5));
        breeds.add(new Breed(109,"Greater Swiss Mountain Dog",4,5));
        breeds.add(new Breed(110,"Greyhound",5,4));
        breeds.add(new Breed(112,"Hamiltonstövare",5,4));
        breeds.add(new Breed(113,"Harrier",5,3));
        breeds.add(new Breed(114,"Havanese",3,2));
        breeds.add(new Breed(115,"Ibizan Hound",5,3));
        breeds.add(new Breed(116,"Icelandic Sheepdog",4,3));
        breeds.add(new Breed(117,"Irish Red and White Setter",5,3));
        breeds.add(new Breed(118,"Irish Setter",5,4));
        breeds.add(new Breed(119,"Irish Terrier",5,3));
        breeds.add(new Breed(120,"Irish Water Spaniel",5,3));
        breeds.add(new Breed(121,"Irish Wolfhound",4,5));
        breeds.add(new Breed(122,"Italian Greyhound",4,2));
        breeds.add(new Breed(123,"Japanese Chin",2,1));
        breeds.add(new Breed(111,"Jack Russell Terrier",5,2));
        breeds.add(new Breed(124,"Jindo",4,3));
        breeds.add(new Breed(125,"Karelian Bear Dog",4,3));
        breeds.add(new Breed(126,"Keeshond",3,3));
        breeds.add(new Breed(127,"Kerry Blue Terrier",4,3));
        breeds.add(new Breed(128,"Komondor",4,4));
        breeds.add(new Breed(129,"Kuvasz",5,5));
        breeds.add(new Breed(130,"Labrador Retriever",5,4));
        breeds.add(new Breed(131,"Lagotto Romagnolo",4,2));
        breeds.add(new Breed(132,"Lakeland Terrier",4,2));
        breeds.add(new Breed(133,"Lancashire Heeler",3,1));
        breeds.add(new Breed(134,"Leonberger",4,5));
        breeds.add(new Breed(135,"Lhasa Apso",3,1));
        breeds.add(new Breed(136,"Löwchen",4,1));
        breeds.add(new Breed(137,"Maltese",2,1));
        breeds.add(new Breed(138,"Manchester Terrier",4,2));
        breeds.add(new Breed(139,"Manchester Terrier Toy",3,1));
        breeds.add(new Breed(140,"Mastiff",4,5));
        breeds.add(new Breed(141,"Miniature Pinscher",4,1));
        breeds.add(new Breed(142,"Miniature Schnauzer",5,2));
        breeds.add(new Breed(143,"Mudi",4,2));
        breeds.add(new Breed(144,"Neapolitan Mastiff",3,5));
        breeds.add(new Breed(145,"Kooikerhondje",5,3));
        breeds.add(new Breed(146,"Newfoundland",3,5));
        breeds.add(new Breed(147,"Norfolk Terriers",4,1));
        breeds.add(new Breed(148,"Nowegian Buhund",5,3));
        breeds.add(new Breed(149,"Norwegian Elkhound",5,3));
        breeds.add(new Breed(150,"Norwegian Lundehund",5,3));
        breeds.add(new Breed(151,"Norwich Terriers",4,1));
        breeds.add(new Breed(152,"Nova Scotia Duck Tolling Retriever",5,3));
        breeds.add(new Breed(153,"Old English Sheepdog",4,4));
        breeds.add(new Breed(154,"Otterhound",5,4));
        breeds.add(new Breed(155,"Papillon",4,1));
        breeds.add(new Breed(156,"Pekingese",2,1));
        breeds.add(new Breed(157,"Pembroke Welsh Corgi",4,2));
        breeds.add(new Breed(158,"Petit Basset Griffon Vendéen",5,2));
        breeds.add(new Breed(159,"Pharaoh Hound",4,3));
        breeds.add(new Breed(160,"Plott",4,3));
        breeds.add(new Breed(161,"Pointer",5,3));
        breeds.add(new Breed(162,"Polish Lowland Sheepdog",4,3));
        breeds.add(new Breed(163,"Pomeranian",2,1));
        breeds.add(new Breed(164,"Poodle",4,4));
        breeds.add(new Breed(165,"Pug",2,2));
        breeds.add(new Breed(166,"Rat Terrier",4,1));
        breeds.add(new Breed(167,"Redbone Coonhound",5,3));
        breeds.add(new Breed(168,"Rhodesian Ridgeback",5,4));
        breeds.add(new Breed(169,"Rottweiler",4,3));
        breeds.add(new Breed(170,"Saint Bernard",3,5));
        breeds.add(new Breed(171,"Saluki",5,4));
        breeds.add(new Breed(172,"Samoyed",4,3));
        breeds.add(new Breed(173,"Schipperke",5,1));
        breeds.add(new Breed(174,"Scottish Deerhound",5,5));
        breeds.add(new Breed(175,"Scottish Terrier",4,2));
        breeds.add(new Breed(176,"Sealyham Terrier",4,2));
        breeds.add(new Breed(177,"Shetland Sheepdog",4,1));
        breeds.add(new Breed(178,"Shiba Inu",3,2));
        breeds.add(new Breed(179,"Shih Tzu",2,1));
        breeds.add(new Breed(180,"Siberian Husky",5,3));
        breeds.add(new Breed(181,"Silky Terrier",3,1));
        breeds.add(new Breed(182,"Skye Terrier",3,2));
        breeds.add(new Breed(183,"Small Munsterlander Pointer",5,3));
        breeds.add(new Breed(184,"Smooth Fox Terrier",5,2));
        breeds.add(new Breed(185,"Soft Coated Wheaten Terrier",4,3));
        breeds.add(new Breed(186,"Stabyhoun",4,3));
        breeds.add(new Breed(187,"Staffordshire Bull Terrier",4,2));
        breeds.add(new Breed(188,"Standard Schnauzer",5,3));
        breeds.add(new Breed(189,"Sussex Spaniel",2,2));
        breeds.add(new Breed(190,"Svensk Lapphund",5,3));
        breeds.add(new Breed(191,"Swedish Vallhund Västgötaspets",5,3));
        breeds.add(new Breed(192,"Tibetan Mastiff",3,5));
        breeds.add(new Breed(193,"Tibetan Spaniel",4,1));
        breeds.add(new Breed(194,"Tibetan Terrier",5,2));
        breeds.add(new Breed(195,"Toy Fox Terrier",5,1));
        breeds.add(new Breed(196,"Treeing Tennessee Brindle",5,3));
        breeds.add(new Breed(197,"Treeing Walker Coonhound",5,4));
        breeds.add(new Breed(198,"Vizsla",5,3));
        breeds.add(new Breed(199,"Weimaraner",5,4));
        breeds.add(new Breed(200,"Welsh Springer Spaniel",5,3));
        breeds.add(new Breed(201,"Welsh Terrier",5,2));
        breeds.add(new Breed(202,"West Highland White Terrie",4,2));
        breeds.add(new Breed(203,"Whippet",5,3));
        breeds.add(new Breed(204,"Wire Fox Terrier",5,2));
        breeds.add(new Breed(205,"Wirehaired Pointing Griffon",4,3));
        breeds.add(new Breed(206,"Working Kelpie",5,3));
        breeds.add(new Breed(207,"Xoloitzcuintli",3,3));
        breeds.add(new Breed(208,"Yorkshire Terrier",4,1));
    }

    public List<Breed> getAllBreeds(){
        return this.filteredBreeds;
    }

    public void setAll(List<Breed> breeds) {
        this.filteredBreeds = breeds;
    }

    public Breed get(int id){
        return filteredBreeds.get(id);
    }

    public int getIndexOf(int position) {
        return this.breeds.indexOf(this.filteredBreeds.get(position));
    }

    public void update(int id, Breed breed) {
        this.breeds.set(id, breed);
        this.filteredBreeds = this.breeds;
    }

    public List<Breed> resetFilteredList() {
        this.filteredBreeds = this.breeds;
        return this.getAllBreeds();
    }

}
